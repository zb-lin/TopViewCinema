package com.lzb.www.pojo.vo;

import com.lzb.www.pojo.po.Order;

import java.time.LocalDateTime;

public class Orders {
    private Order order;
    private String movieName;
    private LocalDateTime startTime;

    public Orders(Order order, String movieName, LocalDateTime startTime) {
        this.order = order;
        this.movieName = movieName;
        this.startTime = startTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order=" + order +
                ", movieName='" + movieName + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
