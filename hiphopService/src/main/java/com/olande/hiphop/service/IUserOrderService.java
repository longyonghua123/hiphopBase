package com.olande.hiphop.service;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.UserOrder;

import java.util.Map;

/**
 * 用户订单服务接口
 */
public interface IUserOrderService {

    /**
     * 根据条件分页查询订单信息
     *
     * @return
     */
    PageData<UserOrder> selectOrderByPage(PageData<UserOrder> page);

    /**
     * 查询符合条件的订单数量
     *
     * @param criteriaMap
     * @return
     */
    int selectPerformCount(Map<String, String> criteriaMap);

    /**添加订单
     * @param order
     * @return
     */
    int insertOrder(UserOrder order);

    /**
     * 根据订单ID查询订单信息
     * @param orderId
     * @return
     */
    UserOrder getOrder(String orderId);
}
