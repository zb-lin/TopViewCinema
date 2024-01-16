package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.middle.MiddleOrderService;
import com.lzb.www.pojo.po.Order;
import com.lzb.www.pojo.vo.Orders;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.OrderService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

import java.time.LocalDateTime;

@Hosted
public class MiddleOrderServiceImpl implements MiddleOrderService {
    @LoggerProxy
    private OrderService orderService;

    @Override
    public byte[] insertOrder(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.OrderRequest orderRequest = RPCProto.OrderRequest.parseFrom(bytes);
        RPCProto.OrderRequest.InsertOrderInfo insertOrderInfo = orderRequest.getInsertOrderInfo();
        RPCProto.Order order = insertOrderInfo.getOrder();
        Order order1 = new Order(order.getId(), order.getTicketId(), order.getUserId(), order.getSeat(),
                order.getPaymentMethod(), order.getPrice(), LocalDateTime.now(), order.getStatus());
        boolean result = orderService.insertOrder(order1);
        RPCProto.OrderResponse.InsertOrderResult insertOrderResult = RPCProto.OrderResponse.InsertOrderResult.newBuilder()
                .setResult(result).build();
        return RPCProto.OrderResponse.newBuilder().setInsertOrderResult(insertOrderResult).build().toByteArray();
    }

    @Override
    public byte[] listOrders(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.OrderRequest orderRequest = RPCProto.OrderRequest.parseFrom(bytes);
        RPCProto.OrderRequest.ListOrdersInfo listOrdersInfo = orderRequest.getListOrdersInfo();
        int currentPage = listOrdersInfo.getCurrentPage();
        int pageSize = listOrdersInfo.getPageSize();
        int id = listOrdersInfo.getId();
        PageBean<Object> pageBean = orderService.listOrders(currentPage, pageSize, id);

        RPCProto.OrderPageBean.Builder orderPageBean = RPCProto.OrderPageBean.newBuilder();
        for (Object row : pageBean.getRows()) {
            Orders orders = (Orders) row;
            Order order = orders.getOrder();
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
                    .setMovieName(orders.getMovieName())
                    .setStartTime(orders.getStartTime().toString()).build();
            orderPageBean.addOrders(orders1);
        }
        orderPageBean.setTotalCount(Integer.parseInt(String.valueOf(pageBean.getTotalCount())));
        RPCProto.OrderResponse.ListOrdersResult listOrdersResult = RPCProto.OrderResponse.ListOrdersResult.newBuilder()
                .setPageBean(orderPageBean).build();
        return RPCProto.OrderResponse.newBuilder().setListOrdersResult(listOrdersResult).build().toByteArray();
    }

    @Override
    public byte[] returnTicket(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.OrderRequest orderRequest = RPCProto.OrderRequest.parseFrom(bytes);
        RPCProto.OrderRequest.ReturnTicketInfo returnTicketInfo = orderRequest.getReturnTicketInfo();
        int id = returnTicketInfo.getId();
        boolean result = orderService.returnTicket(id);
        RPCProto.OrderResponse.ReturnTicketResult returnTicketResult = RPCProto.OrderResponse.ReturnTicketResult.newBuilder()
                .setResult(result).build();
        return RPCProto.OrderResponse.newBuilder().setReturnTicketResult(returnTicketResult).build().toByteArray();
    }

    @Override
    public byte[] deleteOrder(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.OrderRequest orderRequest = RPCProto.OrderRequest.parseFrom(bytes);
        RPCProto.OrderRequest.DeleteOrderInfo deleteOrderInfo = orderRequest.getDeleteOrderInfo();
        int id = deleteOrderInfo.getId();
        boolean result = orderService.deleteOrder(id);
        RPCProto.OrderResponse.DeleteOrderResult deleteOrderResult = RPCProto.OrderResponse.DeleteOrderResult.newBuilder()
                .setResult(result).build();
        return RPCProto.OrderResponse.newBuilder().setDeleteOrderResult(deleteOrderResult).build().toByteArray();
    }

}
