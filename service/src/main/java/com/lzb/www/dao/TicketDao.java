package com.lzb.www.dao;

import com.lzb.www.pojo.po.Ticket;

import java.util.List;

public interface TicketDao {
    boolean insertTicket(Ticket ticket);

    List<Object> listTicket(int id);

    boolean deleteTicket(int id);

    boolean updateTicket(Ticket ticket);

    boolean updateTicketWithLock(Ticket ticket);

    Ticket getTicket(Long id);

    boolean updateStatus(Long id,int status);


}
