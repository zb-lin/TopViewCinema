package com.lzb.www.pojo.po;

import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

@TableName("tb_comment")
public class Comment {
    /**
     * id 主键
     */
    private Integer id;
    /**
     * 对应电影id
     */
    @ColumnName("movie_id")
    private Integer movieId;
    /**
     * 发布评论者 id
     */
    @ColumnName("user_id")
    private Integer userId;
    /**
     * 评分
     */
    private Integer evaluate;
    /**
     * 评论内容
     */
    private String comment;

    public Comment(Integer id, Integer movieId, Integer userId, Integer evaluate, String comment) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.evaluate = evaluate;
        this.comment = comment;
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "Comment{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", evaluate=" + evaluate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
