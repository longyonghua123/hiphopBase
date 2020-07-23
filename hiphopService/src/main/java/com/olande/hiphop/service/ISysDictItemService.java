package com.olande.hiphop.service;

import com.olande.hiphop.entity.SysDictItem;

import java.util.List;

/**
 * 字典项名称
 */
public interface ISysDictItemService {
    /**
     * 添加字典项
     *
     * @param dictItem
     * @return
     */
    int insertDictItem(SysDictItem dictItem);

    /**
     * 根据使用状态查询一级字典项
     *
     * @param useStatus
     * @return
     */
    List<SysDictItem> queryMainDictItem(int useStatus);

    /**
     * 根据父字典项代码和使用状态查询子字典项
     *
     * @param useStatus
     * @param pDictCode
     * @return
     */
    List<SysDictItem> querySubDictItem(int useStatus, String pDictCode);

    /**
     * 修改使用状态
     *
     * @param useStatus
     * @param dictId
     * @param updateSysUserId 修改系统用户ID
     * @return
     */
    int updateUseStatus(int useStatus, String dictId, String updateSysUserId);

    /**
     * 根据字典项ID删除字典项
     *
     * @param dictId
     * @return
     */
    int deleteDictItem(String dictId);

    /**
     * 修改字典项
     *
     * @param dictItem
     * @return
     */
    int updateDictItem(SysDictItem dictItem);

    /**
     * 查询一级字典项名称是否使用
     *
     * @param dictName
     * @return
     */
    int checkMainDictName(String dictName);

    /**
     * 查询二级字典项名称是否使用
     *
     * @param dictName
     * @param pDictCode
     * @return
     */
    int checkSubDictName(String dictName, String pDictCode);

    /**根据字典ID加载字典信息
     * @param dictId
     * @return
     */
    SysDictItem getSysDictItem(String dictId);
}
