package com.lzb.www.pojo.po;

import com.lzb.www.util.type.annotation.NotEmpty;
import com.lzb.www.util.type.annotation.NotNull;
import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

import java.time.LocalDateTime;

@TableName("tb_ticket")
public class Ticket {
    /**
     * 订票id
     */
    private Long id;
    /**
     * 电影id
     */
    @ColumnName("movie_id")
    private Integer movieId;
    /**
     * 电影场次
     */
    @NotEmpty
    @ColumnName("film_sessions")
    private String filmSessions;
    /**
     * 开始时间
     */
    @NotNull
    @ColumnName("start_time")
    private LocalDateTime startTime;
    /**
     * 可选座位
     */
    @NotEmpty
    @ColumnName("optional_seats")
    private String optionalSeats;
    /**
     * 已选座位
     */
    @NotEmpty
    @ColumnName("selected_seats")
    private String selectedSeats;
    /**
     * 剩余票数
     */
    @ColumnName("left_ticket")
    private Integer leftTicket;
    /**
     * 0 存在  1 已删除
     */
    private Integer status;

    public Ticket(Long id, Integer movieId, String filmSessions, LocalDateTime startTime,
                  String optionalSeats, String selectedSeats, Integer leftTicket, Integer status) {
        this.id = id;
        this.movieId = movieId;
        this.filmSessions = filmSessions;
        this.startTime = startTime;
        this.optionalSeats = optionalSeats;
        this.selectedSeats = selectedSeats;
        this.leftTicket = leftTicket;
        this.status = status;
    }

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getFilmSessions() {
        return filmSessions;
    }

    public void setFilmSessions(String filmSessions) {
        this.filmSessions = filmSessions;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getOptionalSeats() {
        return optionalSeats;
    }

    public void setOptionalSeats(String optionalSeats) {
        this.optionalSeats = optionalSeats;
    }

    public String getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(String selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public Integer getLeftTicket() {
        return leftTicket;
    }

    public void setLeftTicket(Integer leftTicket) {
        this.leftTicket = leftTicket;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", filmSessions='" + filmSessions + '\'' +
                ", startTime=" + startTime +
                ", optionalSeats='" + optionalSeats + '\'' +
                ", selectedSeats='" + selectedSeats + '\'' +
                ", leftTicket=" + leftTicket +
                ", status=" + status +
                '}';
    }
}
