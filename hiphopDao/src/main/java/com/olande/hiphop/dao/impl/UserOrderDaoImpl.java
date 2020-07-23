package com.olande.hiphop.dao.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.olande.common.entity.PageData;
import com.olande.hiphop.dao.IUserOrderDao;
import com.olande.hiphop.entity.UserOrder;
import com.olande.hiphop.mapper.UserOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("userOrderDao")
public class UserOrderDaoImpl implements IUserOrderDao {
    @Autowired
    @Qualifier("userOrderMapper")
    private UserOrderMapper userOrderMapper;

    public UserOrderDaoImpl() {
        super();
    }

    @Override
    public PageData<UserOrder> selectOrderByPage(PageData<UserOrder> page) {
        QueryWrapper<UserOrder> wrapper=getOrderQueryWrapper(page.getCriteriaMap());
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
        return (PageData<UserOrder>) userOrderMapper.selectPage(page,wrapper);
    }

    @Override
    public int selectOrderCount(Map<String, String> criteriaMap) {
        QueryWrapper<UserOrder> wrapper = getOrderQueryWrapper(criteriaMap);
        return userOrderMapper.selectCount(wrapper);
    }

    @Override
    public int insertOrder(UserOrder order) {
        return userOrderMapper.insert(order);
    }

    /**
     * 获取订单查询条件包装对象
     *
     * @param criteriaMap
     * @return
     */
    private QueryWrapper<UserOrder> getOrderQueryWrapper(Map<String, String> criteriaMap) {
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        if (criteriaMap.containsKey("status")) {
            int status = Integer.parseInt(criteriaMap.get("status"));
            wrapper.eq("status", status);
        }
        return wrapper;
    }

    @Override
    public UserOrder getOrder(String orderId) {
        return userOrderMapper.selectById(orderId);
    }
}
