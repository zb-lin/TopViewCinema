package com.lzb.www.api;

public interface TicketService {

    byte[] insertTicket(byte[] bytes);

    byte[] listTicket(byte[] bytes);

    byte[] deleteTicket(byte[] bytes);

    byte[] updateTicket(byte[] bytes);

    byte[] updateSeat(byte[] bytes);

}
