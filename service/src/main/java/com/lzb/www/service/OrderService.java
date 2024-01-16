package com.lzb.www.service;

import com.lzb.www.pojo.po.Order;
import com.lzb.www.pojo.vo.PageBean;

public interface OrderService {
    /**
     * 购票(修改对应订单状态)
     *
     * @param order 订单对象
     * @return 是否订票成功
     */
    boolean insertOrder(Order order);

    /**
     * 分页查询某一用户的历史订单
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param id          用户id
     * @return 订单集合
     */
    PageBean<Object> listOrders(int currentPage, int pageSize, int id);

    /**
     * 退票(修改订单状态)
     *
     * @param id 订单id
     * @return 是否修改成功
     */
    boolean returnTicket(int id);

    /**
     * 删除过期订单(修改订单状态, 不再显示)
     *
     * @param id 订单id
     * @return 是否修改成功
     */
    boolean deleteOrder(int id);
}
