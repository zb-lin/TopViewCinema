package com.lzb.www.dao.impl;

import com.lzb.www.dao.TicketDao;
import com.lzb.www.pojo.po.Ticket;
import sspring.bean.annotation.Hosted;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Hosted
public class TicketDaoImpl extends BaseDao implements TicketDao {
    @Override
    public boolean insertTicket(Ticket ticket) {
        return query.insert(ticket);
    }

    @Override
    public List<Object> listTicket(int id) {
        String sql = "select * from tb_ticket where movie_id = ? and status = 0 and start_time > ?";
        return query.queryRows(sql, Ticket.class, new Object[]{id, LocalDateTime.now()});
    }

    @Override
    public boolean deleteTicket(int id) {
        return query.delete(Ticket.class, id);
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        return query.update(ticket, new String[]{"filmSessions", "startTime", "optionalSeats", "leftTicket", "selectedSeats"});
    }

    @Override
    public boolean updateTicketWithLock(Ticket ticket) {
        String sql = "update tb_ticket set optional_seats = ?,selected_seats = ? , left_ticket = ? where id = ? and left_ticket > 0";
        return query.update(sql, new Object[]{ticket.getOptionalSeats(), ticket.getSelectedSeats(), ticket.getLeftTicket(), ticket.getId()});
    }

    @Override
    public Ticket getTicket(Long id) {
        String sql = "select * from tb_ticket where id = ?";
        List<Object> tickets = query.queryRows(sql, Ticket.class, new Object[]{id});
        return Optional.ofNullable(tickets).isPresent() ? (Ticket) tickets.get(0) : null;
    }

    @Override
    public boolean updateStatus(Long id, int status) {
        String sql = "update tb_ticket set status = ? where id = ?";
        return query.update(sql, new Object[]{status, id});

    }
}
