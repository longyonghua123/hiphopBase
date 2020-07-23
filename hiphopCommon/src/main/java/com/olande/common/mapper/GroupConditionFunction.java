package com.olande.common.mapper;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import java.util.function.Function;

/**
 * 用于设置一组查询条件
 *
 * @param <T>
 * @param <R>
 */
public class GroupConditionFunction<T, R> implements Function<T, R> {
    AbstractWrapper conditionWrapper = null;

    public GroupConditionFunction(AbstractWrapper conditionWrapper) {
        this.conditionWrapper = conditionWrapper;
    }

    public GroupConditionFunction() {
    }

    public AbstractWrapper getConditionWrapper() {
        return conditionWrapper;
    }

    public void setConditionWrapper(AbstractWrapper conditionWrapper) {
        this.conditionWrapper = conditionWrapper;
    }

    /**
     * 设置一组相等的OR查询条件
     *
     * @param column 字段名称
     * @param values 字段值范围数组
     */
    public void setEqs(String column, Object... values) {
        if (conditionWrapper == null || values == null) {
            return;
        }
        int lastIndex = values.length - 1;
        if (-1 == lastIndex) {
            return;
        }
        for (int index = 0; index < lastIndex; index++) {
            conditionWrapper.eq(column, values[index]);
            conditionWrapper.or();
        }
        //设置最后一个
        conditionWrapper.eq(column, values[lastIndex]);
    }

    /**
     * 设置like查询条件
     *
     * @param keyword 关键字
     * @param columns 字段名数据
     */
    public void setLikes(String keyword, String... columns) {
        if (conditionWrapper == null || columns == null) {
            return;
        }
        int lastIndex = columns.length - 1;
        if (-1 == lastIndex) {
            return;
        }
        for (int index = 0; index < lastIndex; index++) {
            conditionWrapper.like(columns[index], keyword);
            conditionWrapper.or();
        }
        //设置最后一个
        conditionWrapper.like(columns[lastIndex], keyword);
    }


    /**
     * 设置一组相等的OR查询条件
     *
     * @param column 字段名称
     * @param values 字段值范围数组
     */
    public void setLikes(String column, Object... values) {
        if (conditionWrapper == null || values == null) {
            return;
        }
        int lastIndex = values.length - 1;
        if (-1 == lastIndex) {
            return;
        }
        for (int index = 0; index < lastIndex; index++) {
            conditionWrapper.like(column, values[index]);
            conditionWrapper.or();
        }
        //设置最后一个
        conditionWrapper.like(column, values[lastIndex]);
    }


    @Override
    public R apply(T t) {
        AbstractWrapper wrapper = null;
        if (t instanceof QueryWrapper) {
            wrapper = (QueryWrapper<R>) t;
        } else if (t instanceof UpdateWrapper) {
            wrapper = (UpdateWrapper<R>) t;
        } else {
        }
        if (conditionWrapper != null && wrapper != null) {
            return (R) conditionWrapper;
        } else {
            throw new IllegalArgumentException("组合查询条件设置错误");
        }
    }

}
