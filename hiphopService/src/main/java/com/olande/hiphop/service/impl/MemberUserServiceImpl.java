package com.olande.hiphop.service.impl;

import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.common.util.*;
import com.olande.common.util.mail.EmailService;
import com.olande.hiphop.dao.IMemberUserDao;
import com.olande.hiphop.entity.MemberUser;
import com.olande.hiphop.service.IMemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service("memberUserService")
public class MemberUserServiceImpl implements IMemberUserService {
    @Autowired
    @Qualifier("memberUserDao")
    private IMemberUserDao memberUserDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public int userReg(String email, String password) {
        MemberUser user = new MemberUser();
        password = getPasswordMD5(password);
        String memberId = StringUtils.getUUID32();
        user.setEmail(email);
        user.setPassword(password);
        user.setMemberId(memberId);
        user.setUserStatus(1);
        user.setRegTime(new Date());
        return memberUserDao.userReg(user);
    }

    @Override
    public int checkEmail(String email) {
        return memberUserDao.checkEmail(email);
    }

    @Override
    public boolean sendRegEmail(String email) {
        try {
            String subject = "注册验证码";
            // 随机产生6个数字验证码
            String codes = RandomCodeUtils.getCheckCode();
            //将验证码放入缓存
            redisUtils.set(email, codes, 180);
            String sendHtml = "欢迎注册HipHop文化交流网站会员,你的验证码是:<span style='color:#FF0000'>" + codes + "</span>";
            EmailService.sentMailHtml(subject, sendHtml, email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送注册验证码失败", e);
            redisUtils.del(email);
            return false;
        }

    }

    @Override
    public int checkEmailCode(String email, String code) {
        if (!redisUtils.hasKey(email)) {
            //已过期
            return 9;
        }
        String originCode = redisUtils.get(email).toString();
        if (originCode.equalsIgnoreCase(code)) {
            //正确
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public MemberUser userLogin(String email, String password) {
        password = getPasswordMD5(password);
        return memberUserDao.userLogin(email, password);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int sendRestPasswordEmail(String email, String url) {
        int status = checkEmail(email);
        if (0 == status) {
            //邮箱不存在
            return 0;
        }
        if (redisUtils.hasKey("reset_pwd_key_" + email)) {
            //重置密码邮箱已发送至邮箱
            return 2;
        }
        // 当邮件存在时,发送电子邮件
        try {
            Map<String, String> paramMap = new HashMap<>();
            String memberId = getMemberId(email);
            paramMap.put("email", email);
            paramMap.put("member_id", memberId);
            SignUtils signUtils = new SignUtils();
            paramMap.put("nonce_str", signUtils.getNonceStr());
            long expire = signUtils.getTimeExpire(15);
            paramMap.put("expire", String.valueOf(expire));
            String sign = signUtils.getSign(paramMap);
            //将签名写入缓存
            String key = "reset_pwd_key_" + email;
            String value = sign;
            redisUtils.set(key, value, 900);
            StringBuffer paramBuf = new StringBuffer();
            paramBuf.append('?');
            Iterator<String> keys = paramMap.keySet().iterator();
            paramBuf.append("sign=" + sign);
            while (keys.hasNext()) {
                String k = keys.next();
                String v = paramMap.get(k);
                paramBuf.append("&" + k + "=" + v);
            }
            // 绑定参数
            url += paramBuf.toString();
            String subject = "找回密码";
            String sendHtml = "请点击以下超链接修改密码:<a href=\"?\">?</a>";
            sendHtml = sendHtml.replaceAll("[?]", url);
            EmailService.sentMailHtml(subject, sendHtml, email);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            redisUtils.del("reset_pwd_key_" + email);
            return -1;
        }
    }

    @Override
    public JSONData updatePassword(String memberId, String oldPwd, String newPwd) {
        boolean eq = oldPwd.equals(newPwd);
        oldPwd = getPasswordMD5(oldPwd);
        int count = memberUserDao.countByPassword(memberId, oldPwd);
        if (0 == count) {
            return JSONData.FAIL(100600, "旧密码不正确");
        }
        if (eq) {
            return JSONData.FAIL(100610, "旧密码与新密码相等");
        }
        newPwd = getPasswordMD5(newPwd);
        MemberUser member = new MemberUser();
        member.setMemberId(memberId);
        member.setPassword(newPwd);
        int rows = memberUserDao.updateMemberUser(member);
        if (1 == rows) {
            return JSONData.SUCCESS(1);
        } else {
            return JSONData.FAIL(100620, "修改密码时出现未知状态");
        }
    }


    @Override
    public JSONData checkUsername(String username) {
        username = username.toLowerCase();
        int count = memberUserDao.countByUsername(username);
        if (0 == count) {
            return JSONData.SUCCESS("success");
        } else if (1 == count) {
            return JSONData.FAIL(200, "用户名已被使用");
        } else {
            return JSONData.FAIL(999, "检查用户名是否被使用,服务器返回未知状态");
        }
    }

    @Override
    public JSONData setUsername(String username, String memberId) {
        int count = memberUserDao.countByUsername(username);
        if (1 == count) {
            return JSONData.FAIL(200, "用户名已被使用");
        }
        if (0 != count) {
            return JSONData.FAIL(999, "检查用户名是否被使用,服务器返回未知状态");
        }
        MemberUser member = new MemberUser();
        member.setUsername(username);
        member.setMemberId(memberId);
        int result = memberUserDao.updateMemberUser(member);
        return JSONData.SUCCESS(result);
    }


    @Override
    public JSONData updateHeadImg(String memberId, String headImg) {
        MemberUser member = new MemberUser();
        member.setHeadImg(headImg);
        member.setMemberId(memberId);
        int result = memberUserDao.updateMemberUser(member);
        return JSONData.SUCCESS(result);
    }

    @Override
    public JSONData setNickname(String nickname, String memberId) {
        MemberUser member = new MemberUser();
        member.setNickname(nickname);
        member.setMemberId(memberId);
        int result = memberUserDao.updateMemberUser(member);
        return JSONData.SUCCESS(result);
    }

    @Override
    public String getMemberId(String email) {
        return memberUserDao.getMemberId(email);
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    private static String getPasswordMD5(String password) {
        for (int i = 1; i <= 5; i++) {
            password = EncryptUtils.getMD5(password + "http://www.hiphop.com?i=" + i);
        }
        return password;
    }


    @Override
    public JSONData resetPassword(Map<String, String> paramMap) {
        String email = paramMap.get("email");
        String memberId = paramMap.get("member_id");
        String nonceStr = paramMap.get("nonce_str");
        String expire = paramMap.get("expire");
        String sign = paramMap.get("sign");
        // 判断是否缺少参数
        if (StringUtils.emptyStrs(email, memberId, nonceStr, expire, sign)) {
            return JSONData.FAIL(400, "缺少参数");
        }
        SignUtils signUtils = new SignUtils();
        paramMap.remove("sign");
        // 判断签名是否错误
        String checkedSign = signUtils.getSign(paramMap);
        if (!checkedSign.equals(sign)) {
            return JSONData.FAIL(401, "签名错误");
        }
        // 判断重置密码时间是否失效
        if (!redisUtils.hasKey("reset_pwd_key_" + email)) {
            return JSONData.FAIL(402, "重置密码链接已失效");
        }
        redisUtils.del("reset_pwd_key_" + email);
        return JSONData.SUCCESS(0);
    }

    @Override
    public PageData<MemberUser> selectMemberByPage(PageData<MemberUser> page) {
        Map<String, String> criteriaMap = page.getCriteriaMap();
        if (criteriaMap.containsKey("user_status")) {
            int userStatus = Integer.parseInt(criteriaMap.get("user_status"));
            if (!(1 == userStatus || 2 == userStatus || 0 == userStatus)) {
                throw new IllegalArgumentException("会员用户状态错误");
            }
        }
        long total = memberUserDao.selectMemberCount(criteriaMap);
        page.setTotal(total);
        return memberUserDao.selectMemberByPage(page);
    }

    @Override
    public int setUserStatus(int userStatus, String memberId) {
        if (!(2 == userStatus || 1 == userStatus)) {
            throw new IllegalArgumentException("会员用户状态错误");
        }
        return memberUserDao.setUserStatus(userStatus, memberId);
    }


}
