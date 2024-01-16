package com.lzb.www.constant;

/**
 * 常量类
 */
public class WebConstant {
    private WebConstant() {
    }

    public static final String LOGIN_HTML = "/TopViewCinema/login.html";
    public static final String LOGIN_INTERFACE = "/user/login";
    public static final String LOGIN_HTML_PATH = "http://localhost:8080/TopViewCinema/login.html";
    public static final String PHOTO_FOLDER = "/img";
    public static final String NOT_LOGIN = "未登录";
    public static final String QUERY_SUCCESS = "查询成功";
    public static final String QUERY_ERROR = "查询失败";
    public static final String RETURN_SUCCESS = "退票成功";
    public static final String RETURN_ERROR = "退票失败";
    public static final String INSERT_SUCCESS = "添加成功";
    public static final String INSERT_ERROR = "添加失败";
    public static final String ORDER_SUCCESS = "购票成功";
    public static final String ORDER_ERROR = "该票已被购买, 请重新选择";
    public static final String UPDATE_SUCCESS = "修改成功";
    public static final String UPDATE_ERROR = "修改失败";
    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_ERROR = "删除失败";
    public static final String SUMMIT_SUCCESS = "提交成功";
    public static final String SUMMIT_ERROR = "提交失败";
    public static final String LOGIN_ERROR = "手机号码格式或密码错误";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String PAGE_SIZE = "pageSize";
    public static final String ID = "id";
    public static final String UTF_8 = "utf-8";
    public static final String TOKEN = "token";
    public static final String PARAM = "param";
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    public static final String PHOTO_FILE = "file";
    public static final String DATE_TIME_FORMATTER = "yyyy-MM-dd-hh-mm-ss";
    public static final String UPDATE_TICKET_ERROR = "该场次目前不可修改";
    public static final String UPDATE_SEAT_ERROR = "修改出错, 请确认操作";
    public static final String STATUS = "status";
    public static final String ADMIN = "admin";
    public static final String UPLOAD_IMG_FOLDER_URL = "C:\\Users\\86177\\Desktop\\TopViewCinema\\client\\src\\main\\webapp\\upload";
    public static final long MAX_FILE_SIZE = 1024 * 1024 * 20;
    public static final String DEFAULT_ERROR_MESSAGE = "系统繁忙, 请稍后再试";
    public static final int PAGINATION_QUERY = 1;
    public static final int COMMON_QUERY = 0;

}


