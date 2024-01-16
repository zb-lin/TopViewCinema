package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.middle.MiddleTicketService;
import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.pojo.vo.Seat;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.TicketService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

import java.time.LocalDateTime;
import java.util.List;

@Hosted
public class MiddleTicketServiceImpl implements MiddleTicketService {
    @LoggerProxy
    private TicketService ticketService;

    @Override
    public byte[] insertTicket(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.TicketRequest ticketRequest = RPCProto.TicketRequest.parseFrom(bytes);
        RPCProto.TicketRequest.InsertTicketInfo insertTicketInfo = ticketRequest.getInsertTicketInfo();
        RPCProto.Ticket ticket = insertTicketInfo.getTicket();
        int id = insertTicketInfo.getId();
        Ticket ticket1 = new Ticket(ticket.getId(), ticket.getMovieId(), ticket.getFilmSessions(),
                LocalDateTime.parse(ticket.getStartTime()), ticket.getOptionalSeats(), ticket.getSelectedSeats(),
                ticket.getLeftTicket(), ticket.getStatus());
        boolean result = ticketService.insertTicket(ticket1, id);

        RPCProto.TicketResponse.InsertTicketResult insertTicketResult = RPCProto.TicketResponse.InsertTicketResult.newBuilder()
                .setResult(result).build();
        return RPCProto.TicketResponse.newBuilder().setInsertTicketResult(insertTicketResult).build().toByteArray();
    }

    @Override
    public byte[] listTicket(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.TicketRequest ticketRequest = RPCProto.TicketRequest.parseFrom(bytes);
        RPCProto.TicketRequest.ListTicketInfo listTicketInfo = ticketRequest.getListTicketInfo();
        String id = listTicketInfo.getId();
        List<Object> tickets = ticketService.listTicket(Integer.parseInt(id));
        RPCProto.TicketResponse.ListTicketResult.Builder listTicketResult = RPCProto.TicketResponse.ListTicketResult.newBuilder();
        if (tickets == null) {
            return RPCProto.TicketResponse.newBuilder().setListTicketResult(listTicketResult).build().toByteArray();
        }
        for (Object row : tickets) {
            Ticket ticket = (Ticket) row;
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
        }
        return RPCProto.TicketResponse.newBuilder().setListTicketResult(listTicketResult).build().toByteArray();
    }

    @Override
    public byte[] deleteTicket(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.TicketRequest ticketRequest = RPCProto.TicketRequest.parseFrom(bytes);
        RPCProto.TicketRequest.DeleteTicketInfo deleteTicketInfo = ticketRequest.getDeleteTicketInfo();
        long id = deleteTicketInfo.getId();

        boolean result = ticketService.deleteTicket(id);
        RPCProto.TicketResponse.DeleteTicketResult deleteTicketResult = RPCProto.TicketResponse.DeleteTicketResult.newBuilder()
                .setResult(result).build();
        return RPCProto.TicketResponse.newBuilder().setDeleteTicketResult(deleteTicketResult).build().toByteArray();
    }

    @Override
    public byte[] updateTicket(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.TicketRequest ticketRequest = RPCProto.TicketRequest.parseFrom(bytes);
        RPCProto.TicketRequest.UpdateTicketInfo updateTicketInfo = ticketRequest.getUpdateTicketInfo();
        RPCProto.Ticket ticket = updateTicketInfo.getTicket();
        Ticket ticket1 = new Ticket(ticket.getId(), ticket.getMovieId(), ticket.getFilmSessions(),
                LocalDateTime.parse(ticket.getStartTime()), ticket.getOptionalSeats(), ticket.getSelectedSeats(),
                ticket.getLeftTicket(), ticket.getStatus());

        boolean result = ticketService.updateTicket(ticket1);
        RPCProto.TicketResponse.UpdateTicketResult updateTicketResult = RPCProto.TicketResponse.UpdateTicketResult.newBuilder()
                .setResult(result).build();
        return RPCProto.TicketResponse.newBuilder().setUpdateTicketResult(updateTicketResult).build().toByteArray();
    }

    @Override
    public byte[] updateSeat(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.TicketRequest ticketRequest = RPCProto.TicketRequest.parseFrom(bytes);
        RPCProto.TicketRequest.UpdateSeatInfo updateSeatInfo = ticketRequest.getUpdateSeatInfo();
        RPCProto.Seat seat = updateSeatInfo.getSeat();
        Seat seat1 = new Seat(seat.getSeat(), seat.getOperate(), seat.getTicketId());
        boolean result = ticketService.updateSeat(seat1);
        RPCProto.TicketResponse.UpdateSeatResult updateSeatResult = RPCProto.TicketResponse.UpdateSeatResult.newBuilder()
                .setResult(result).build();
        return RPCProto.TicketResponse.newBuilder().setUpdateSeatResult(updateSeatResult).build().toByteArray();
    }

}
