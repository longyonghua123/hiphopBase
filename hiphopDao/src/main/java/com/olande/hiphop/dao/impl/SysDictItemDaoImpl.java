package com.olande.hiphop.dao.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.hiphop.dao.ISysDictItemDao;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.mapper.SysDictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("sysDictItemDao")
public class SysDictItemDaoImpl implements ISysDictItemDao {
    @Autowired
    @Qualifier("sysDictItemMapper")
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public int insertDictItem(SysDictItem dictItem) {
        return sysDictItemMapper.insert(dictItem);
    }

    @Override
    public List<SysDictItem> queryMainDictItem(int useStatus) {
        QueryWrapper<SysDictItem> wrapper = new QueryWrapper<>();
        if (2 == useStatus || 1 == useStatus) {
            wrapper.eq("use_status", useStatus);
        }
        wrapper.isNull("p_dict_code");
        return sysDictItemMapper.selectList(wrapper);
    }

    @Override
    public List<SysDictItem> querySubDictItem(int useStatus, String pDictCode) {
        QueryWrapper<SysDictItem> wrapper = new QueryWrapper<>();
        if (2 == useStatus || 1 == useStatus) {
            wrapper.eq("use_status", useStatus);
        }
        wrapper.eq("p_dict_code", pDictCode);
        return sysDictItemMapper.selectList(wrapper);
    }

    @Override
    public int updateUseStatus(int useStatus, String dictId, String updateSysUserId) {
        UpdateWrapper<SysDictItem> wrapper = new UpdateWrapper<>();
        wrapper.eq("dict_id", dictId);
        SysDictItem dictItem = new SysDictItem();
        dictItem.setUseStatus(useStatus);
        dictItem.setUpdateSysUserId(updateSysUserId);
        return sysDictItemMapper.update(dictItem, wrapper);
    }

    @Override
    public int deleteDictItem(String dictId) {
        return sysDictItemMapper.deleteById(dictId);
    }

    @Override
    public int updateDictItem(SysDictItem dictItem) {
        UpdateWrapper<SysDictItem> wrapper = new UpdateWrapper<>();
        wrapper.eq("dict_id", dictItem.getDictId());
        return sysDictItemMapper.update(dictItem, wrapper);
    }

    @Override
    public int checkMainDictName(String dictName) {
        QueryWrapper<SysDictItem> wrapper = new QueryWrapper<>();
        wrapper.isNull("p_dict_code");
        wrapper.eq("dict_name", dictName);
        return sysDictItemMapper.selectCount(wrapper);
    }

    @Override
    public int checkSubDictName(String dictName, String pDictCode) {
        QueryWrapper<SysDictItem> wrapper = new QueryWrapper<>();
        wrapper.eq("p_dict_code", pDictCode);
        wrapper.eq("dict_name", dictName);
        return sysDictItemMapper.selectCount(wrapper);
    }

    @Override
    public SysDictItem getSysDictItem(String dictId) {
        return sysDictItemMapper.selectById(dictId);
    }

    @Override
    public List<SysDictItem> selectSysDictItemByCodes(String... dictCodes) {
        if (ArrayUtil.isEmpty(dictCodes)) {
            return new ArrayList<>();
        }
        QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
        int last = dictCodes.length - 1;
        if (0 == last) {
            queryWrapper.eq("dict_code", dictCodes[last]);
            return sysDictItemMapper.selectList(queryWrapper);
        }
        int lastSecond = last - 1;//倒数第二个
        for (int index = 0; index <= lastSecond; index++) {
            queryWrapper.eq("dict_code", dictCodes[index]);
            queryWrapper.or();
        }
        queryWrapper.eq("dict_code", dictCodes[last]);
        return sysDictItemMapper.selectList(queryWrapper);
    }
}
