package com.olande.hiphop.service.impl;

import com.olande.common.util.StringUtils;
import com.olande.hiphop.dao.ISysDictItemDao;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.service.ISysDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("sysDictItemService")
public class SysDictItemServiceImpl implements ISysDictItemService {
    @Autowired
    @Qualifier("sysDictItemDao")
    private ISysDictItemDao sysDictItemDao;

    public SysDictItemServiceImpl() {
        super();
    }

    @Override
    public int insertDictItem(SysDictItem dictItem) {
        String dictId = StringUtils.getUUID32();
        dictItem.setDictId(dictId);
        dictItem.setUseStatus(1);
        dictItem.setAddTime(new Date());
        return sysDictItemDao.insertDictItem(dictItem);
    }

    @Override
    public List<SysDictItem> queryMainDictItem(int useStatus) {
        if (0 != useStatus) {
            checkUseStatus(useStatus);
        }
        return sysDictItemDao.queryMainDictItem(useStatus);
    }

    @Override
    public List<SysDictItem> querySubDictItem(int useStatus, String pDictCode) {
        if (0 != useStatus) {
            checkUseStatus(useStatus);
        }
        return sysDictItemDao.querySubDictItem(useStatus, pDictCode);
    }

    @Override
    public int updateUseStatus(int useStatus, String dictId, String updateSysUserId) {
        checkUseStatus(useStatus);
        return sysDictItemDao.updateUseStatus(useStatus, dictId, updateSysUserId);
    }

    @Override
    public int deleteDictItem(String dictId) {
        return sysDictItemDao.deleteDictItem(dictId);
    }

    @Override
    public int updateDictItem(SysDictItem dictItem) {
        checkUseStatus(dictItem.getUseStatus());
        dictItem.setUpdateTime(new Date());
        return sysDictItemDao.updateDictItem(dictItem);
    }

    @Override
    public int checkMainDictName(String dictName) {
        return sysDictItemDao.checkMainDictName(dictName);
    }

    @Override
    public int checkSubDictName(String dictName, String pDictCode) {
        return sysDictItemDao.checkSubDictName(dictName, pDictCode);
    }

    /**
     * 检测字典项使用状态
     * 1:使用;0:注销
     *
     * @param useStatus
     */
    private void checkUseStatus(int useStatus) {
        if (!(1 == useStatus || 2 == useStatus)) {
            throw new IllegalArgumentException("字典项使用状态错误");
        }
    }

    @Override
    public SysDictItem getSysDictItem(String dictId) {
        return sysDictItemDao.getSysDictItem(dictId);
    }
}
