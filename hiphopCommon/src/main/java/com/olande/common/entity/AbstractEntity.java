package com.olande.common.entity;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
/**
 * 实体类父类
 */
public abstract class AbstractEntity implements Serializable {
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
