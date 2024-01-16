package com.lzb.www.pojo.po;

import com.lzb.www.util.type.annotation.NotEmpty;
import com.lzb.www.util.type.annotation.NotNull;
import com.lzb.www.util.type.annotation.Range;
import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

import java.time.LocalDateTime;

@TableName("tb_order")
public class Order {
    /**
     * 订单id
     */
    private Integer id;
    /**
     * 对应电影票id
     */
    @NotNull
    @ColumnName("ticket_id")
    private Long ticketId;
    /**
     * 用户id
     */
    @NotNull
    @ColumnName("user_id")
    private Integer userId;
    /**
     * 座位
     */
    @NotNull
    @Range(min = 0, max = 60)
    private Integer seat;
    /**
     * 支付方式
     * 0 会员卡 可打折  1 微信支付
     * 2 支付宝        3 银行卡
     */
    @NotEmpty
    @ColumnName("payment_method")
    private String paymentMethod;
    /**
     * 价格
     */
    @NotNull
    private Double price;
    /**
     * 过期时间
     */
    @ColumnName("expiration_Time")
    private LocalDateTime expirationTime;
    /**
     * 0 未看, 1 已看, 2 已过期, 3 已退票  4 不显示  5 未购买  6 废弃
     */
    private Integer status;

    public Order(Integer id, Long ticketId, Integer userId, Integer seat, String paymentMethod, Double price,
                 LocalDateTime expirationTime, Integer status) {
        this.id = id;
        this.ticketId = ticketId;
        this.userId = userId;
        this.seat = seat;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.expirationTime = expirationTime;
        this.status = status;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                ", seat='" + seat + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", price=" + price +
                ", expirationTime=" + expirationTime +
                ", status=" + status +
                '}';
    }
}
