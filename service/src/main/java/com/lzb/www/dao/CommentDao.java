package com.lzb.www.dao;

import com.lzb.www.pojo.po.Comment;

import java.util.List;

public interface CommentDao {
    boolean insertComment(Comment comment);

    List<Object> listComment(int begin, int size, int movieId);

    Number countComment(int movieId);
}
