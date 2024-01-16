package com.lzb.www.pojo.vo;

public class EvaluateDate {
    private Integer movieId;
    private Integer userId;
    private Integer evaluate;
    private String comment;

    public EvaluateDate(Integer movieId, Integer userId, Integer evaluate, String comment) {
        this.movieId = movieId;
        this.userId = userId;
        this.evaluate = evaluate;
        this.comment = comment;
    }

    public EvaluateDate() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EvaluateDate{" +
                "movieId=" + movieId +
                ", userId=" + userId +
                ", evaluate=" + evaluate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
