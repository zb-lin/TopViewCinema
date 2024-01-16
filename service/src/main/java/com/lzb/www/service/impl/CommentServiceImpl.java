package com.lzb.www.service.impl;

import com.lzb.www.dao.CommentDao;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.service.CommentService;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;

import java.util.List;

import static com.lzb.www.constant.GlobalConstant.INT_ONE;

@Hosted
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;


    @Override
    public PageBean<Object> listComment(int currentPage, int pageSize, int movieId) {
        int begin = (currentPage - INT_ONE) * pageSize;
        List<Object> rows = commentDao.listComment(begin, pageSize, movieId);
        Number count = commentDao.countComment(movieId);
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(count);
        return pageBean;
    }

}
