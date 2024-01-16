package com.lzb.www.controller;

import com.lzb.www.api.OrderService;
import com.lzb.www.pojo.po.Order;
import com.lzb.www.pojo.vo.Orders;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.ProtoBufUtil;
import sspring.bean.annotation.Hosted;
import com.lzb.www.annotation.Param;
import sspring.bean.annotation.MockBean;
import sspring.bean.annotation.RpcProxy;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import static com.lzb.www.constant.WebConstant.*;

@Hosted
@WebServlet("/order/*")
public class OrderServlet extends BaseServlet {

//    @MockBean
    @RpcProxy
    private OrderService orderService;

    public Response<?> insertOrder(@Param Order order) throws IOException {
        RPCProto.OrderRequest orderRequest = ProtoBufUtil.insertOrderRequest(order);
        byte[] bytes = orderService.insertOrder(orderRequest.toByteArray());
        RPCProto.OrderResponse orderResponse = RPCProto.OrderResponse.parseFrom(bytes);
        return Response.response(ORDER_SUCCESS, ORDER_ERROR, orderResponse.getInsertOrderResult().getResult());
    }

    public Response<?> listOrder(@Param(CURRENT_PAGE) Integer currentPage, @Param(PAGE_SIZE) Integer pageSize,
                                 @Param(ID) Integer id) throws IOException {
        RPCProto.OrderRequest orderRequest = ProtoBufUtil.listOrdersRequest(currentPage, pageSize, id);
        byte[] bytes = orderService.listOrders(orderRequest.toByteArray());
        PageBean<Orders> ordersPageBean = ProtoBufUtil.listOrdersResponse(bytes);
        return Response.response(ordersPageBean, QUERY_SUCCESS, QUERY_ERROR, bytes != null);
    }

    public Response<?> returnTicket(@Param(ID) Integer id) throws IOException {
        RPCProto.OrderRequest orderRequest = ProtoBufUtil.returnTicketRequest(id);
        byte[] bytes = orderService.returnTicket(orderRequest.toByteArray());
        RPCProto.OrderResponse orderResponse = RPCProto.OrderResponse.parseFrom(bytes);
        return Response.response(RETURN_SUCCESS, RETURN_ERROR, orderResponse.getReturnTicketResult().getResult());
    }

    public Response<?> deleteOrder(@Param(ID) Integer id) throws IOException {
        RPCProto.OrderRequest orderRequest = ProtoBufUtil.deleteOrderRequest(id);

        byte[] bytes = orderService.deleteOrder(orderRequest.toByteArray());
        RPCProto.OrderResponse orderResponse = RPCProto.OrderResponse.parseFrom(bytes);
        return Response.response(DELETE_SUCCESS, DELETE_ERROR, orderResponse.getDeleteOrderResult().getResult());
    }

}
