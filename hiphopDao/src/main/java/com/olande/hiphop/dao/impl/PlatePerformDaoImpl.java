package com.olande.hiphop.dao.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.olande.common.entity.PageData;
import com.olande.hiphop.dao.IPlatePerformDao;
import com.olande.hiphop.entity.PlatePerform;
import com.olande.hiphop.mapper.PlatePerformMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("platePerformDao")
public class PlatePerformDaoImpl implements IPlatePerformDao {
    @Autowired
    @Qualifier("platePerformMapper")
    private PlatePerformMapper platePerformMapper;

    @Override
    public int insertPerform(PlatePerform perform) {
        return platePerformMapper.insert(perform);
    }

    @Override
    public PlatePerform getPerform(String performId) {
        return platePerformMapper.selectById(performId);
    }

    @Override
    public List<PlatePerform> selectPerformByType(int performStatus, String dictCode) {
        QueryWrapper<PlatePerform> wrapper = new QueryWrapper<>();
        if (0 != performStatus) {
            //演出状态
            wrapper.eq("perform_status", performStatus);
        }
        //演出分类,
        wrapper.eq("dict_code", dictCode);
        //根据时间降序
        wrapper.orderByAsc("add_time");
        return platePerformMapper.selectList(wrapper);
    }

    @Override
    public int updatePerformStatus(String performId, int performStatus) {
        UpdateWrapper<PlatePerform> wrapper = new UpdateWrapper<>();
        wrapper.eq("perform_id", performId);
        PlatePerform perform = new PlatePerform();
        perform.setPerformStatus(performStatus);
        return platePerformMapper.update(perform, wrapper);
    }

    @Override
    public int deletePerform(String performId) {
        return platePerformMapper.deleteById(performId);
    }

    @Override
    public List<PlatePerform> selectPerformByKW(int performStatus, String keyword) {
        QueryWrapper<PlatePerform> wrapper = new QueryWrapper<>();
        if (0 != performStatus) {
            //演出状态
            wrapper.eq("perform_status", performStatus);
        }
        wrapper.and(kw -> kw.like("tags", keyword).or().like("perform_context", keyword).or().like("perform_title", keyword));
        wrapper.orderByAsc("add_time");
        return platePerformMapper.selectList(wrapper);
    }

    @Override
    public List<PlatePerform> selectPerform(int performStatus) {
        QueryWrapper<PlatePerform> wrapper = new QueryWrapper<>();
        if (0 != performStatus) {
            //演出状态
            wrapper.eq("perform_status", performStatus);
        }
        //根据时间降序
        wrapper.orderByAsc("add_time");
        return platePerformMapper.selectList(wrapper);
    }

    @Override
    public PageData<PlatePerform> selectPerformByPage(PageData<PlatePerform> page) {
        QueryWrapper<PlatePerform> wrapper = getPerformQueryWrapper(page.getCriteriaMap());
        Map<String, Boolean> orderMap = page.getOrderMap();
        if (MapUtil.isNotEmpty(orderMap)) {
            orderMap.forEach((columnName, orderType) -> {
                if (orderType) {
                    wrapper.orderByAsc(columnName);
                } else {
                    wrapper.orderByDesc(columnName);
                }
            });
        }
        return (PageData<PlatePerform>) platePerformMapper.selectPage(page, wrapper);
    }

    @Override
    public int selectPerformCount(Map<String, String> criteriaMap) {
        QueryWrapper<PlatePerform> wrapper = getPerformQueryWrapper(criteriaMap);
        return platePerformMapper.selectCount(wrapper);
    }

    /**
     * 获取查询演出条件包装对象
     *
     * @param criteriaMap
     * @return
     */
    private QueryWrapper<PlatePerform> getPerformQueryWrapper(Map<String, String> criteriaMap) {
        QueryWrapper<PlatePerform> wrapper = new QueryWrapper<>();
        //设置查询条件
        if (criteriaMap.containsKey("perform_status")) {
            int performStatus = Integer.parseInt(criteriaMap.get("perform_status"));
            if (0 != performStatus)
                wrapper.eq("perform_status", performStatus);
        }
        return wrapper;
    }

    @Override
    public Map<String, Object> getPerformMap(String performId) {
        return platePerformMapper.getPerformMap(performId);
    }
}
