package com.lzb.www.controller.mock;

import com.lzb.www.api.TicketService;
import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.proto.RPCProto;
import sspring.bean.annotation.Hosted;

import java.time.LocalDateTime;

@Hosted
public class MockTicketServiceImpl implements TicketService {


    @Override
    public byte[] insertTicket(byte[] bytes) {
        RPCProto.TicketResponse.InsertTicketResult insertTicketResult = RPCProto.TicketResponse.InsertTicketResult.newBuilder()
                .setResult(true).build();
        return RPCProto.TicketResponse.newBuilder().setInsertTicketResult(insertTicketResult).build().toByteArray();
    }

    @Override
    public byte[] listTicket(byte[] bytes) {
        Ticket ticket = new Ticket(10000L, 1, "1", LocalDateTime.now(),
                "[1,2,3]", "[]", 0, 0);
        RPCProto.TicketResponse.ListTicketResult.Builder listTicketResult = RPCProto.TicketResponse.ListTicketResult.newBuilder();
        RPCProto.Ticket ticket1 = RPCProto.Ticket.newBuilder()
                .setId(ticket.getId())
                .setMovieId(ticket.getMovieId())
                .setFilmSessions(ticket.getFilmSessions())
                .setStartTime(ticket.getStartTime().toString())
                .setOptionalSeats(ticket.getOptionalSeats())
                .setSelectedSeats(ticket.getSelectedSeats())
                .setLeftTicket(ticket.getLeftTicket())
                .setStatus(ticket.getStatus()).build();
        listTicketResult.addTicket(ticket1);
        return RPCProto.TicketResponse.newBuilder().setListTicketResult(listTicketResult).build().toByteArray();
    }

    @Override
    public byte[] deleteTicket(byte[] bytes) {
        RPCProto.TicketResponse.DeleteTicketResult deleteTicketResult = RPCProto.TicketResponse.DeleteTicketResult.newBuilder()
                .setResult(true).build();
        return RPCProto.TicketResponse.newBuilder().setDeleteTicketResult(deleteTicketResult).build().toByteArray();
    }

    @Override
    public byte[] updateTicket(byte[] bytes) {
        RPCProto.TicketResponse.UpdateTicketResult updateTicketResult = RPCProto.TicketResponse.UpdateTicketResult.newBuilder()
                .setResult(true).build();
        return RPCProto.TicketResponse.newBuilder().setUpdateTicketResult(updateTicketResult).build().toByteArray();
    }

    @Override
    public byte[] updateSeat(byte[] bytes) {
        RPCProto.TicketResponse.UpdateSeatResult updateSeatResult = RPCProto.TicketResponse.UpdateSeatResult.newBuilder()
                .setResult(true).build();
        return RPCProto.TicketResponse.newBuilder().setUpdateSeatResult(updateSeatResult).build().toByteArray();
    }

}
