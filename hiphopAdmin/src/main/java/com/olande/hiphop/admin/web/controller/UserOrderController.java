package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.PageData;
import com.olande.hiphop.entity.UserOrder;
import com.olande.hiphop.service.IUserOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员用户订单控制器
 */
@Slf4j
@Controller
@RequestMapping("order")
@Api(value = "订单管理")
public class UserOrderController extends BaseController {
    @Autowired
    @Qualifier("userOrderService")
    private IUserOrderService userOrderService;

    /**
     * 根据条件分页查询订单信息
     *
     * @return
     */
    @PostMapping(value = "query_page")
    public String selectOrderByPage(PageData<UserOrder> page, HttpServletRequest request) {
        setPageQueryConfig(request, page);
        page = userOrderService.selectOrderByPage(page);
        request.setAttribute("page", page);
        return "order/order_list";
    }


    /**
     * 根据订单ID查询订单信息
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "order_info")
    String getOrder(@RequestParam("order_id") String orderId, Model model) {
        UserOrder order = userOrderService.getOrder(orderId);
        model.addAttribute("order", order);
        return "order/info";

    }
}
