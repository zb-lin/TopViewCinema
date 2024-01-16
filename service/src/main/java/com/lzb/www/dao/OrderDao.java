package com.lzb.www.dao;

import com.lzb.www.pojo.po.Order;

import java.util.List;

public interface OrderDao {
    boolean insertOrder(Order order);

    List<Object> listOrders(int begin, int size, int id);

    Number countOrders(int id);

    boolean update(Order order);

    Order getOrder(int id);

    List<Object> listOrders(Long ticketId,int status);

    boolean updateOrder(Order order);

    Order getOrder(Long ticketId, int seat);

}
