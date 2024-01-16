package com.lzb.www.pojo.vo;

import java.util.List;

/**
 * 分页查询的javabean
 */
public class PageBean<T> {
    /**
     * 总记录数
     */
    private Number totalCount;

    /**
     * 当前页数据
     */
    private List<T> rows;

    public Number getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Number totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", rows=" + rows +
                '}';
    }
}
