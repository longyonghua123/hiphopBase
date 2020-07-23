package com.olande.hiphop.dao;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.PlatePerform;

import java.util.List;
import java.util.Map;

/**
 * 演出Dao接口
 */
public interface IPlatePerformDao {

    /**
     * 添加演出信息
     *
     * @param perform
     * @return
     */
    int insertPerform(PlatePerform perform);

    /**
     * 根据演出ID查询演出信息
     *
     * @param performId
     * @return
     */
    PlatePerform getPerform(String performId);


    /**
     * 根据分类代码查询演出
     *
     * @param performStatus   演出状态(0:全部;1:销售中;2:已下架;3.已过期)
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    List<PlatePerform> selectPerformByType(int performStatus, String dictCode);

    /**
     * 根据关键字查询演出信息
     *
     * @param performStatus  演出状态(0:全部;1:销售中;2:已下架;3.已过期)
     * @param keyword 关键字
     * @return
     */
    List<PlatePerform> selectPerformByKW(int performStatus, String keyword);

    /**
     * 查询演出信息
     *
     * @param performStatus 演出状态(0:全部;1:销售中;2:已下架;3.已过期)
     * @return
     */
    List<PlatePerform> selectPerform(int performStatus);

    /**
     * 修改演出记录状态
     *
     * @param performId 演出ID
     * @param performStatus    演出状态
     * @return
     */
    int updatePerformStatus(String performId, int performStatus);

    /**
     * 根据演出ID删除演出
     * 说明:审核中不可以删除
     *
     * @param performId
     * @return
     */
    int deletePerform(String performId);

    /**
     * 根据条件分页查询演出信息
     *
     * @return
     */
    PageData<PlatePerform> selectPerformByPage(PageData<PlatePerform> page);

    /**
     * 查询符合条件的演出数量
     * @param criteriaMap
     * @return
     */
    int selectPerformCount(Map<String, String> criteriaMap);

    /**
     * 根据演出ID查询演出详细信息,返回Map
     *
     * @param performId
     * @return
     */
    Map<String, Object> getPerformMap( String performId);
}
