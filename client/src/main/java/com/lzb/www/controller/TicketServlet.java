package com.lzb.www.controller;

import com.lzb.www.api.TicketService;
import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.pojo.vo.Seat;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.ProtoBufUtil;
import sspring.bean.annotation.Hosted;
import com.lzb.www.annotation.Param;
import sspring.bean.annotation.MockBean;
import sspring.bean.annotation.RpcProxy;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

import static com.lzb.www.constant.WebConstant.*;

@Hosted
@WebServlet("/ticket/*")
public class TicketServlet extends BaseServlet {

//    @MockBean
    @RpcProxy
    private TicketService ticketService;

    public Response<?> insertTicket(@Param(ID) Integer id, @Param Ticket ticket) throws IOException {
        RPCProto.TicketRequest ticketRequest = ProtoBufUtil.insertTicketRequest(id, ticket);
        byte[] bytes = ticketService.insertTicket(ticketRequest.toByteArray());
        RPCProto.TicketResponse ticketResponse = RPCProto.TicketResponse.parseFrom(bytes);
        return Response.response(INSERT_SUCCESS, INSERT_ERROR, ticketResponse.getInsertTicketResult().getResult());
    }

    public Response<?> listTicket(@Param(ID) Integer id) throws IOException {
        RPCProto.TicketRequest ticketRequest = ProtoBufUtil.listTicketRequest(id);
        byte[] bytes = ticketService.listTicket(ticketRequest.toByteArray());
        List<Ticket> tickets = ProtoBufUtil.listTicketResponse(bytes);
        return Response.response(tickets, QUERY_SUCCESS, QUERY_ERROR, bytes != null);
    }

    public Response<?> deleteTicket(@Param(ID) Long id) throws IOException {
        RPCProto.TicketRequest ticketRequest = ProtoBufUtil.deleteTicketRequest(id);
        byte[] bytes = ticketService.deleteTicket(ticketRequest.toByteArray());
        RPCProto.TicketResponse ticketResponse = RPCProto.TicketResponse.parseFrom(bytes);
        return Response.response(DELETE_SUCCESS, DELETE_ERROR, ticketResponse.getDeleteTicketResult().getResult());
    }

    public Response<?> updateTicket(@Param Ticket ticket) throws IOException {
        RPCProto.TicketRequest ticketRequest = ProtoBufUtil.updateTicketRequest(ticket);
        byte[] bytes = ticketService.updateTicket(ticketRequest.toByteArray());
        RPCProto.TicketResponse ticketResponse = RPCProto.TicketResponse.parseFrom(bytes);
        return Response.response(UPDATE_SUCCESS, UPDATE_TICKET_ERROR, ticketResponse.getUpdateTicketResult().getResult());
    }

    public Response<?> updateSeat(@Param Seat seat) throws IOException {
        RPCProto.TicketRequest ticketRequest = ProtoBufUtil.updateSeatRequest(seat);
        byte[] bytes = ticketService.updateSeat(ticketRequest.toByteArray());
        RPCProto.TicketResponse ticketResponse = RPCProto.TicketResponse.parseFrom(bytes);
        return Response.response(UPDATE_SUCCESS, UPDATE_SEAT_ERROR, ticketResponse.getUpdateSeatResult().getResult());
    }
}
