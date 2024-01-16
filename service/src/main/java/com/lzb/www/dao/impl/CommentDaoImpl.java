package com.lzb.www.dao.impl;

import com.lzb.www.dao.CommentDao;
import com.lzb.www.pojo.po.Comment;
import sspring.bean.annotation.Hosted;

import java.util.List;

@Hosted
public class CommentDaoImpl extends BaseDao implements CommentDao {
    @Override
    public boolean insertComment(Comment comment) {
        return query.insert(comment);
    }

    @Override
    public List<Object> listComment(int begin, int size, int movieId) {
        String sql = "select * from tb_comment where movie_id = ? limit ?,?";
        return query.queryRows(sql, Comment.class, new Object[]{movieId, begin, size});
    }

    @Override
    public Number countComment(int movieId) {
        String sql = "select count(*) from tb_comment where movie_id = ?";
        return query.queryNumber(sql, new Object[]{movieId});
    }
}
