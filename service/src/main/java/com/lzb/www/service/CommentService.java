package com.lzb.www.service;

import com.lzb.www.pojo.vo.PageBean;

public interface CommentService {
    /**
     * 分页查询评论
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param movieId     电影id
     * @return 评论集合
     */
    PageBean<Object> listComment(int currentPage, int pageSize, int movieId);

}
