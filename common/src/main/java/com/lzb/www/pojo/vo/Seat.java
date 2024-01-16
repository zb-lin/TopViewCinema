package com.lzb.www.pojo.vo;

public class Seat {
    private Integer seat;
    private Integer operate;
    private Long ticketId;

    public Seat(Integer seat, Integer operate, Long ticketId) {
        this.seat = seat;
        this.operate = operate;
        this.ticketId = ticketId;
    }

    public Seat() {
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seat=" + seat +
                ", operate=" + operate +
                ", ticketId=" + ticketId +
                '}';
    }
}
