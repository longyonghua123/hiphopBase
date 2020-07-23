package com.olande.hiphop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.olande.common.entity.PageData;
import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.IPlatePerformDao;
import com.olande.hiphop.entity.PlatePerform;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.entity.SysUser;
import com.olande.hiphop.service.CommonService;
import com.olande.hiphop.service.IPlatePerformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("platePerformService")
public class PlatePerformServiceImpl extends CommonService implements IPlatePerformService {
    @Autowired
    @Qualifier("platePerformDao")
    private IPlatePerformDao platePerformDao;

    @Override
    public int insertPerform(PlatePerform perform) {
        String performId = StringUtils.getUUID32();
        perform.setPerformId(performId);
        perform.setPerformStatus(2);//默认下架
        perform.setAddTime(new Date());
        return platePerformDao.insertPerform(perform);
    }

    @Override
    public PlatePerform getPerform(String performId) {
        return platePerformDao.getPerform(performId);
    }

    @Override
    public List<PlatePerform> selectPerformByType(int performStatus, String dictCode) {
        if (0 != performStatus)
            checkPerformStatus(performStatus);
        return platePerformDao.selectPerformByType(performStatus, dictCode);
    }

    @Override
    public List<PlatePerform> selectPerformByKW(int performStatus, String keyword) {
        if (0 != performStatus)
            checkPerformStatus(performStatus);
        return platePerformDao.selectPerformByKW(performStatus, keyword);
    }

    @Override
    public List<PlatePerform> selectPerform(int performStatus) {
        if (0 != performStatus)
            checkPerformStatus(performStatus);
        return platePerformDao.selectPerform(performStatus);
    }

    @Override
    public int updatePerformStatus(String performId, int performStatus) {
        checkPerformStatus(performStatus);
        return platePerformDao.updatePerformStatus(performId, performStatus);
    }

    @Override
    public int deletePerform(String performId) {
        PlatePerform perform = platePerformDao.getPerform(performId);
        if (perform == null) {
            //文章已删除
            return -1;
        }
        int performStatus = perform.getPerformStatus();
        if (1 == performStatus || 2 == performStatus) {
            //正常、已下架的文章可以删除
            return platePerformDao.deletePerform(performId);
        } else {
            //文章状态错误
            return performStatus;
        }
    }

    /**
     * 查询时检测演出状态
     *
     * @param performStatus 演出状态(全部;1:销售中;2:已下架;3.已过期)
     */
    private void checkPerformStatus(int performStatus) {
        if (performStatus < 1 || performStatus > 3) {
            throw new IllegalArgumentException("演出状态错误!【状态=" + performStatus + "】");
        }
    }

    @Override
    public PageData<PlatePerform> selectPerformByPage(PageData<PlatePerform> page) {
        Map<String, String> criteriaMap = page.getCriteriaMap();
        if (MapUtil.isNotEmpty(criteriaMap) && criteriaMap.containsKey("perform_status")) {
            int performStatus = Integer.parseInt(criteriaMap.get("perform_status"));
            if (0 != performStatus)
                checkPerformStatus(performStatus);
        }
        long total = platePerformDao.selectPerformCount(criteriaMap);
        page.setTotal(total);
        platePerformDao.selectPerformByPage(page);
        List<PlatePerform> performList = page.getRecords();
        if (CollectionUtil.isEmpty(performList)) {
            return page;
        }
        /***设置关联字段**/
        int size = performList.size();
        List<String> sysUserIdList = new ArrayList<>();
        List<String> dictCodeList = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            PlatePerform perform = performList.get(index);
            String dictCode = perform.getDictCode();
            addParams(dictCode, dictCodeList);
            String addSysUserId = perform.getAddSysUserId();
            addParams(addSysUserId, sysUserIdList);
            String updateSysUserId = perform.getUpdateSysUserId();
            addParams(updateSysUserId, sysUserIdList);
        }
        if (!sysUserIdList.isEmpty()) {
            List<SysUser> sysUserList = selectSysUsersByIds(sysUserIdList);
            performList.forEach(perform -> {
                String addSysUserId = perform.getAddSysUserId();
                String updateSysUserId = perform.getUpdateSysUserId();
                SysUser addSysUser = getSysUser(addSysUserId, sysUserList);
                SysUser updateSysUser = getSysUser(updateSysUserId, sysUserList);
                perform.setAddSysUser(addSysUser);
                perform.setUpdateSysUser(updateSysUser);
            });
        }
        if (!dictCodeList.isEmpty()) {
            List<SysDictItem> dictItemList = selectSysDictItemByCodes(dictCodeList);
            performList.forEach(perform -> {
                String dictCode = perform.getDictCode();
                SysDictItem dictItem = getDictItem(dictCode, dictItemList);
                perform.setDictItem(dictItem);
            });
        }
        return page;
    }


    @Override
    public Map<String, Object> getPerformMap(String performId) {
        return platePerformDao.getPerformMap(performId);
    }
}