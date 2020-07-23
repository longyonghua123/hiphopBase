package com.olande.hiphop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.olande.common.entity.PageData;
import com.olande.common.util.DateTimeUtils;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.IUserOrderDao;
import com.olande.hiphop.entity.UserOrder;
import com.olande.hiphop.service.IUserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userOrderService")
public class UserOrderServiceImpl implements IUserOrderService {
    @Autowired
    @Qualifier("userOrderDao")
    private IUserOrderDao userOrderDao;
    private static List<String> list = new ArrayList<>();

    @Override
    public PageData<UserOrder> selectOrderByPage(PageData<UserOrder> page) {
        long total = userOrderDao.selectOrderCount(page.getCriteriaMap());
        page.setTotal(total);
        return userOrderDao.selectOrderByPage(page);
    }

    @Override
    public int selectPerformCount(Map<String, String> criteriaMap) {
        return userOrderDao.selectOrderCount(criteriaMap);
    }

    @Override
    public int insertOrder(UserOrder order) {
        order.setOrderId(StringUtils.getUUID32());
        order.setOrderTime(new Date());
        String orderCode = getOrderCode();
        order.setOrderCode(orderCode);
        return userOrderDao.insertOrder(order);
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    private String getOrderCode() {
        //年(2位)+年中天(3位)+小时(2位)+分钟(2位)+秒(2为)+毫秒(3位)
        String start = DateTimeUtils.format(new Date(), "yyDDDHHmmssSSS");
        //JVM运行毫秒数后6位反转(不够位数补0)
        String end;
        String reNanoStr = StrUtil.reverse(String.valueOf(System.nanoTime()));
        if (reNanoStr.length() >= 6) {
            end = reNanoStr.substring(0, 6);
        } else {
            end = StrUtil.fillAfter(reNanoStr, '0', 5);
        }
        String orderCode = start + end;
        return orderCode;
    }

    @Override
    public UserOrder getOrder(String orderId) {
        return userOrderDao.getOrder(orderId);
    }
}
