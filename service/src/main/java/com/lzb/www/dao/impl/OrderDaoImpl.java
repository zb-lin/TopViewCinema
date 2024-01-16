package com.lzb.www.dao.impl;

import com.lzb.www.dao.OrderDao;
import com.lzb.www.pojo.po.Order;
import sspring.bean.annotation.Hosted;

import java.util.List;
import java.util.Optional;

@Hosted
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public boolean insertOrder(Order order) {
        return query.insert(order);
    }

    @Override
    public List<Object> listOrders(int begin, int size, int id) {
        String sql = "select * from tb_order where user_id = ? and status != 4 order by id desc limit ?,?";
        return query.queryRows(sql, Order.class, new Object[]{id, begin, size});
    }

    @Override
    public Number countOrders(int id) {
        String sql = "select count(*) from tb_order where user_id = ?";
        return query.queryNumber(sql, new Object[]{id});
    }

    @Override
    public boolean update(Order order) {
        return query.update(order, new String[]{"status"});
    }

    @Override
    public Order getOrder(int id) {
        String sql = "select * from tb_order where id = ?";
        List<Object> orders = query.queryRows(sql, Order.class, new Object[]{id});
        return Optional.ofNullable(orders).isPresent() ? (Order) orders.get(0) : null;
    }

    @Override
    public List<Object> listOrders(Long ticketId, int status) {
        String sql = "select seat from tb_order where ticket_id = ? and status = ?";
        return query.queryRows(sql, Order.class, new Object[]{ticketId, status});
    }

    @Override
    public boolean updateOrder(Order order) {
        String sql = "update tb_order set user_id = ?, payment_method=?, price = ?,expiration_Time = ?, status = ? where seat = ? and status = 5";
        return query.update(sql, new Object[]{
                order.getUserId(), order.getPaymentMethod(), order.getPrice(), order.getExpirationTime(), order.getStatus(), order.getSeat()});
    }

    @Override
    public Order getOrder(Long ticketId, int seat) {
        String sql = "select * from tb_order where ticket_id = ? and seat = ?";
        List<Object> orders = query.queryRows(sql, Order.class, new Object[]{ticketId, seat});
        return Optional.ofNullable(orders).isPresent() ? (Order) orders.get(0) : null;
    }
}
