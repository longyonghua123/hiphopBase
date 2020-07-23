package com.olande.hiphop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 用户订单
 */
@Data
@Alias(value = "订单")
@ApiModel(value = "订单")
public class UserOrder {
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "用户(会员用户ID)")
    private String memberUserId;

    @ApiModelProperty(value = "演出ID")
    private String performId;

    @ApiModelProperty(value = "订单时间")
    private Date orderTime;

    @ApiModelProperty(value = "订购数量")
    private Integer num;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "订单备注")
    private String remark;

}
