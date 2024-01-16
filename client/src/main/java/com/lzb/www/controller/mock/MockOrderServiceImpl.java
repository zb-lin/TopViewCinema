package com.lzb.www.controller.mock;

import com.lzb.www.api.OrderService;
import com.lzb.www.pojo.po.Order;
import com.lzb.www.proto.RPCProto;
import sspring.bean.annotation.Hosted;

import java.time.LocalDateTime;

import static com.lzb.www.constant.GlobalConstant.*;

@Hosted
public class MockOrderServiceImpl implements OrderService {


    @Override
    public byte[] insertOrder(byte[] bytes) {
        RPCProto.OrderResponse.InsertOrderResult insertOrderResult = RPCProto.OrderResponse.InsertOrderResult.newBuilder()
                .setResult(true).build();
        return RPCProto.OrderResponse.newBuilder().setInsertOrderResult(insertOrderResult).build().toByteArray();
    }

    @Override
    public byte[] listOrders(byte[] bytes) {
        RPCProto.OrderPageBean.Builder orderPageBean = RPCProto.OrderPageBean.newBuilder();
        Order order = new Order(null, 10000L, DEFAULT_USER_ID, DEFAULT_SEAT, DEFAULT_PAYMENT_METHOD,
                DEFAULT_PRICE, LocalDateTime.now(), NOT_PURCHASED);
        RPCProto.Order order1 = RPCProto.Order.newBuilder()
                .setId(order.getId())
                .setTicketId(order.getTicketId())
                .setUserId(order.getUserId())
                .setSeat(order.getSeat())
                .setPaymentMethod(order.getPaymentMethod())
                .setPrice(order.getPrice())
                .setExpirationTime(order.getExpirationTime().toString())
                .setStatus(order.getStatus()).build();
        RPCProto.Orders orders1 = RPCProto.Orders.newBuilder()
                .setOrder(order1)
                .setMovieName("1")
                .setStartTime(LocalDateTime.now().toString()).build();
        orderPageBean.addOrders(orders1);

        orderPageBean.setTotalCount(1);
        RPCProto.OrderResponse.ListOrdersResult listOrdersResult = RPCProto.OrderResponse.ListOrdersResult.newBuilder()
                .setPageBean(orderPageBean).build();
        return RPCProto.OrderResponse.newBuilder().setListOrdersResult(listOrdersResult).build().toByteArray();
    }

    @Override
    public byte[] returnTicket(byte[] bytes) {
        RPCProto.OrderResponse.ReturnTicketResult returnTicketResult = RPCProto.OrderResponse.ReturnTicketResult.newBuilder()
                .setResult(true).build();
        return RPCProto.OrderResponse.newBuilder().setReturnTicketResult(returnTicketResult).build().toByteArray();
    }

    @Override
    public byte[] deleteOrder(byte[] bytes) {
        RPCProto.OrderResponse.DeleteOrderResult deleteOrderResult = RPCProto.OrderResponse.DeleteOrderResult.newBuilder()
                .setResult(true).build();
        return RPCProto.OrderResponse.newBuilder().setDeleteOrderResult(deleteOrderResult).build().toByteArray();
    }

}
