package com.olande.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON数据结构
 */
@ApiModel(value = "响应JSON数据")
public class JSONData implements Serializable {
    /**
     * 等于0:正常 大于0:业务性错误 小于0:程序运行异常
     */
    @ApiModelProperty(value = "等于0:正常 大于0:业务性错误 小于0:程序运行异常")
    private int status = 0;
    /**
     * 错误信息:status小于0 或大于0时存在
     */
    @ApiModelProperty(value = "错误信息:status小于0 或大于0时存在")
    private String message;
    /**
     * 请求成功时,返回请求相关的数据信息,仅当status=0时存在
     */
    @ApiModelProperty(value = "请求成功时,返回请求相关的数据信息,仅当status=0时存在")
    private Object data;

    private Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 序列号插件
     */
    private SerializerFeature[] sfs;

    public JSONData() {
        super();
    }


    public JSONData(Object data) {
        super();
        this.data = data;
    }

    public JSONData(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public final static JSONData SUCCESS(Object data) {
        return new JSONData(data);
    }

    /**
     * 失败
     *
     * @param status
     * @param message
     * @return
     */
    public final static JSONData FAIL(int status, String message) {
        return new JSONData(status, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONData put(String key, Object value) {
        data = null == data ? map : data;
        map.put(key, value);
        return this;
    }

    /**
     * 获取JSON格式数据
     *
     * @return
     */
    @Override
    public String toString() {
        this.init();
        JSONObject json = new JSONObject();
        json.put("status", this.status);
        if (status == 0) {
            this.data = this.data == null ? new Object[]{} : this.data;
            json.put("data", this.data);
        } else {
            json.put("message", this.message);
        }
        return JSON.toJSONString(json, this.sfs);
    }

    public String toString(String dataFormat) {
        this.init();
        JSONObject json = new JSONObject();
        json.put("status", this.status);
        if (status == 0) {
            this.data = this.data == null ? new Object[]{} : this.data;
            json.put("data", this.data);
        } else {
            json.put("message", this.message);
        }
        return JSON.toJSONStringWithDateFormat(json, dataFormat, this.sfs);
    }

    /**
     * 初始化序列号插件
     */
    private void init() {
        sfs = new SerializerFeature[4];
        sfs[0] = SerializerFeature.WriteNullStringAsEmpty;
        sfs[1] = SerializerFeature.WriteMapNullValue;
        sfs[2] = SerializerFeature.WriteNullBooleanAsFalse;
        sfs[3] = SerializerFeature.WriteNullNumberAsZero;
    }

}
