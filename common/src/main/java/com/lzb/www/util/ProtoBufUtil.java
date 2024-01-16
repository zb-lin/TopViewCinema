package com.lzb.www.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.pojo.po.*;
import com.lzb.www.pojo.vo.EvaluateDate;
import com.lzb.www.pojo.vo.Orders;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.pojo.vo.Seat;
import com.lzb.www.proto.RPCProto;
import rpc.exception.RPCException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lzb.www.constant.WebConstant.COMMON_QUERY;

public class ProtoBufUtil {

    public static RPCProto.UserRequest loginInfoRequest(String phone, String password) {
        RPCProto.UserRequest.LoginInfo loginInfo = RPCProto.UserRequest.LoginInfo.newBuilder()
                .setPhone(phone)
                .setPassword(password).build();
        return RPCProto.UserRequest.newBuilder().setLoginInfo(loginInfo).build();
    }

    public static String loginResponse(byte[] bytes) {
        RPCProto.UserResponse userResponse = null;
        try {
            userResponse = RPCProto.UserResponse.parseFrom(bytes);
            return userResponse.getLoginResult().getResult();
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.UserRequest getUserInfoRequest(int id) {
        RPCProto.UserRequest.GetUserInfo getUserInfo = RPCProto.UserRequest.GetUserInfo.newBuilder()
                .setId(String.valueOf(id)).build();
        return RPCProto.UserRequest.newBuilder().setGetUserInfo(getUserInfo).build();
    }

    public static User getUser(byte[] bytes) {
        RPCProto.UserResponse userResponse = null;
        try {
            userResponse = RPCProto.UserResponse.parseFrom(bytes);
            RPCProto.User user = userResponse.getGetUserResult().getUser();
            return new User(user.getId(), user.getPhone(), user.getPassword(), user.getHead());
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }


    public static RPCProto.User copyUser(User user) {
        return RPCProto.User.newBuilder()
                .setId(user.getId())
                .setPassword(user.getPassword())
                .setPhone(user.getPhone())
                .setHead(user.getHead())
                .build();
    }

    public static RPCProto.UserRequest changePasswordRequest(User user) {
        RPCProto.User user1 = ProtoBufUtil.copyUser(user);
        RPCProto.UserRequest.ChangePasswordInfo changePasswordInfo = RPCProto.UserRequest.ChangePasswordInfo.newBuilder()
                .setUser(user1).build();
        return RPCProto.UserRequest.newBuilder().setChangePasswordInfo(changePasswordInfo).build();
    }

    public static boolean changePasswordResponse(byte[] bytes) {
        RPCProto.UserResponse userResponse = null;
        try {
            userResponse = RPCProto.UserResponse.parseFrom(bytes);
            return userResponse.getChangePasswordResult().getResult();
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.UserRequest uploadHeadRequest(String fileName, int id) {
        RPCProto.UserRequest.UploadHeadInfo uploadHeadInfo = RPCProto.UserRequest.UploadHeadInfo.newBuilder()
                .setFileName(fileName)
                .setId(id)
                .build();
        return RPCProto.UserRequest.newBuilder().setUploadHeadInfo(uploadHeadInfo).build();
    }

    public static RPCProto.RoleRequest listPermissionRequest(int id) {
        RPCProto.RoleRequest.ListPermissionInfo listPermissionInfo = RPCProto.RoleRequest.ListPermissionInfo.newBuilder()
                .setId(id).build();
        return RPCProto.RoleRequest.newBuilder().setListPermissionInfo(listPermissionInfo).build();
    }

    public static RPCProto.RoleResponse.ListPermissionResult listPermissionResponse(byte[] bytes) {
        RPCProto.RoleResponse roleResponse = null;
        try {
            roleResponse = RPCProto.RoleResponse.parseFrom(bytes);
            return roleResponse.getListPermissionResult();
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.CommentRequest listCommentRequest(int currentPage, int pageSize, int movieId) {
        RPCProto.CommentRequest.ListCommentInfo listCommentInfo = RPCProto.CommentRequest.ListCommentInfo.newBuilder()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setMovieId(movieId).build();
        return RPCProto.CommentRequest.newBuilder().setListCommentInfo(listCommentInfo).build();
    }

    public static PageBean<Comment> listCommentResponse(byte[] bytes) {
        RPCProto.CommentResponse commentResponse = null;
        try {
            commentResponse = RPCProto.CommentResponse.parseFrom(bytes);
            RPCProto.CommentPageBean commentPageBean = commentResponse.getListCommentResult().getCommentPageBean();
            List<Comment> comments = new ArrayList<>();
            for (RPCProto.Comment comment : commentPageBean.getCommentList()) {
                Comment comment1 = new Comment(comment.getId(), comment.getMovieId(), comment.getUserId(),
                        comment.getEvaluate(), comment.getComment());
                comments.add(comment1);
            }
            int commentCount = commentPageBean.getCommentCount();
            PageBean<Comment> pageBean = new PageBean<>();
            pageBean.setTotalCount(commentCount);
            pageBean.setRows(comments);
            return pageBean;
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.MovieRequest uploadMovieRequest(Movie movie) {
        RPCProto.Movie movie1 = RPCProto.Movie.newBuilder()
                .setName(movie.getName())
                .setDirector(movie.getDirector())
                .setLeadingRole(movie.getLeadingRole())
                .setType(movie.getType())
                .setProductionCountry(movie.getProductionCountry())
                .setTheTimeOfMovie(movie.getTheTimeOfMovie())
                .setReleaseTime(movie.getReleaseTime().toString())
                .setPrice(movie.getPrice()).build();
        RPCProto.MovieRequest.InsertMovieInfo insertMovieInfo = RPCProto.MovieRequest.InsertMovieInfo.newBuilder()
                .setMovie(movie1).build();
        return RPCProto.MovieRequest.newBuilder().setInsertMovieInfo(insertMovieInfo).build();
    }

    public static RPCProto.MovieRequest listMoviesRequest(int currentPage, int pageSize, int status) {
        RPCProto.MovieRequest.ListMoviesInfo listMoviesInfo = RPCProto.MovieRequest.ListMoviesInfo.newBuilder()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setStatus(status).build();
        return RPCProto.MovieRequest.newBuilder().setListMoviesInfo(listMoviesInfo).build();
    }

    public static PageBean<Movie> listMoviesResponse(byte[] bytes, int status) {
        RPCProto.MovieResponse movieResponse = null;
        try {
            movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
            RPCProto.MoviePageBean moviePageBean;
            if (status == COMMON_QUERY) {
                moviePageBean = movieResponse.getListMoviesResult().getMoviePageBean();
            } else {
                moviePageBean = movieResponse.getListMoviesByParamResult().getMoviePageBean();
            }
            List<Movie> movies = new ArrayList<>();
            for (RPCProto.Movie movie : moviePageBean.getMovieList()) {
                Movie movie1 = new Movie(movie.getId(), movie.getName(), movie.getDirector(), movie.getLeadingRole(), movie.getType(),
                        movie.getProductionCountry(), movie.getTheTimeOfMovie(), movie.getPoster(),
                        LocalDateTime.parse(movie.getReleaseTime()), movie.getPrice(), movie.getEvaluate(), movie.getEvaluateCount());
                movies.add(movie1);
            }
            int movieCount = moviePageBean.getMovieCount();
            PageBean<Movie> pageBean = new PageBean<>();
            pageBean.setTotalCount(movieCount);
            pageBean.setRows(movies);
            return pageBean;
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.MovieRequest uploadPosterRequest(int id, String fileName) {
        RPCProto.MovieRequest.UploadPosterInfo uploadPosterInfo = RPCProto.MovieRequest.UploadPosterInfo.newBuilder()
                .setId(id)
                .setFileName(fileName).build();
        return RPCProto.MovieRequest.newBuilder().setUploadPosterInfo(uploadPosterInfo).build();
    }

    public static RPCProto.MovieRequest updateMovieRequest(Movie movie) {
        RPCProto.Movie movie1 = RPCProto.Movie.newBuilder()
                .setId(movie.getId())
                .setName(movie.getName())
                .setDirector(movie.getDirector())
                .setLeadingRole(movie.getLeadingRole())
                .setType(movie.getType())
                .setProductionCountry(movie.getProductionCountry())
                .setTheTimeOfMovie(movie.getTheTimeOfMovie())
                .setPoster(movie.getPoster())
                .setReleaseTime(movie.getReleaseTime().toString())
                .setPrice(movie.getPrice())
                .setEvaluate(movie.getEvaluate())
                .setEvaluateCount(movie.getEvaluateCount()).build();
        RPCProto.MovieRequest.UpdateMovieInfo updateMovieInfo = RPCProto.MovieRequest.UpdateMovieInfo.newBuilder()
                .setMovie(movie1).build();
        return RPCProto.MovieRequest.newBuilder().setUpdateMovieInfo(updateMovieInfo).build();
    }

    public static RPCProto.MovieRequest deleteMovieRequest(int id) {
        RPCProto.MovieRequest.DeleteMovieInfo deleteMovieInfo = RPCProto.MovieRequest.DeleteMovieInfo.newBuilder()
                .setId(id).build();
        return RPCProto.MovieRequest.newBuilder().setDeleteMovieInfo(deleteMovieInfo).build();
    }

    public static RPCProto.MovieRequest listMoviesByParamRequest(int currentPage, int pageSize, String param) {
        RPCProto.MovieRequest.ListMoviesByParamInfo listMoviesByParamInfo = RPCProto.MovieRequest.ListMoviesByParamInfo.newBuilder()
                .setCurrentPage(currentPage)
                .setPageSize(pageSize)
                .setParam(param).build();
        return RPCProto.MovieRequest.newBuilder().setListMoviesByParamInfo(listMoviesByParamInfo).build();
    }

    public static RPCProto.MovieRequest updateEvaluateRequest(EvaluateDate evaluateDate) {
        RPCProto.EvaluateDate evaluateDate1 = RPCProto.EvaluateDate.newBuilder()
                .setEvaluate(evaluateDate.getEvaluate())
                .setComment(evaluateDate.getComment())
                .setMovieId(evaluateDate.getMovieId())
                .setUserId(evaluateDate.getUserId()).build();
        RPCProto.MovieRequest.UpdateEvaluateInfo updateEvaluateInfo = RPCProto.MovieRequest.UpdateEvaluateInfo.newBuilder()
                .setEvaluateDate(evaluateDate1).build();
        return RPCProto.MovieRequest.newBuilder().setUpdateEvaluateInfo(updateEvaluateInfo).build();
    }

    public static RPCProto.OrderRequest insertOrderRequest(Order order) {
        RPCProto.Order order1 = RPCProto.Order.newBuilder()
                .setTicketId(order.getTicketId())
                .setUserId(order.getUserId())
                .setSeat(order.getSeat())
                .setPaymentMethod(order.getPaymentMethod())
                .setPrice(order.getPrice())
                .setStatus(order.getStatus()).build();
        RPCProto.OrderRequest.InsertOrderInfo insertOrderInfo = RPCProto.OrderRequest.InsertOrderInfo.newBuilder()
                .setOrder(order1).build();
        return RPCProto.OrderRequest.newBuilder().setInsertOrderInfo(insertOrderInfo).build();
    }

    public static RPCProto.OrderRequest listOrdersRequest(int currentPage, int pageSize, int id) {
        RPCProto.OrderRequest.ListOrdersInfo listOrdersInfo = RPCProto.OrderRequest.ListOrdersInfo.newBuilder()
                .setId(id)
                .setCurrentPage(currentPage)
                .setPageSize(pageSize).build();
        return RPCProto.OrderRequest.newBuilder().setListOrdersInfo(listOrdersInfo).build();
    }

    public static PageBean<Orders> listOrdersResponse(byte[] bytes) {
        RPCProto.OrderResponse orderResponse = null;
        try {
            orderResponse = RPCProto.OrderResponse.parseFrom(bytes);
            RPCProto.OrderPageBean pageBean = orderResponse.getListOrdersResult().getPageBean();
            List<RPCProto.Orders> ordersList = pageBean.getOrdersList();
            List<Orders> orders1 = new ArrayList<>();
            for (RPCProto.Orders orders : ordersList) {
                RPCProto.Order order = orders.getOrder();
                Order order1 = new Order(order.getId(), order.getTicketId(), order.getUserId(), order.getSeat(),
                        order.getPaymentMethod(), order.getPrice(), LocalDateTime.parse(order.getExpirationTime()), order.getStatus());
                Orders orders2 = new Orders(order1, orders.getMovieName(), LocalDateTime.parse(orders.getStartTime()));
                orders1.add(orders2);
            }
            int orderCount = pageBean.getOrdersCount();
            PageBean<Orders> pageBean1 = new PageBean<>();
            pageBean1.setRows(orders1);
            pageBean1.setTotalCount(orderCount);
            return pageBean1;
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.OrderRequest returnTicketRequest(int id) {
        RPCProto.OrderRequest.ReturnTicketInfo returnTicketInfo = RPCProto.OrderRequest.ReturnTicketInfo.newBuilder()
                .setId(id).build();
        return RPCProto.OrderRequest.newBuilder().setReturnTicketInfo(returnTicketInfo).build();
    }

    public static RPCProto.OrderRequest deleteOrderRequest(int id) {
        RPCProto.OrderRequest.DeleteOrderInfo deleteOrderInfo = RPCProto.OrderRequest.DeleteOrderInfo.newBuilder()
                .setId(id).build();
        return RPCProto.OrderRequest.newBuilder().setDeleteOrderInfo(deleteOrderInfo).build();
    }

    public static RPCProto.TicketRequest insertTicketRequest(int id, Ticket ticket) {
        RPCProto.Ticket ticket1 = RPCProto.Ticket.newBuilder()
                .setFilmSessions(ticket.getFilmSessions())
                .setStartTime(ticket.getStartTime().toString())
                .setOptionalSeats(ticket.getOptionalSeats())
                .setSelectedSeats(ticket.getSelectedSeats()).build();
        RPCProto.TicketRequest.InsertTicketInfo insertTicketInfo = RPCProto.TicketRequest.InsertTicketInfo.newBuilder()
                .setTicket(ticket1)
                .setId(id).build();
        return RPCProto.TicketRequest.newBuilder().setInsertTicketInfo(insertTicketInfo).build();
    }

    public static RPCProto.TicketRequest listTicketRequest(int id) {
        RPCProto.TicketRequest.ListTicketInfo listTicketInfo = RPCProto.TicketRequest.ListTicketInfo.newBuilder()
                .setId(String.valueOf(id)).build();
        return RPCProto.TicketRequest.newBuilder().setListTicketInfo(listTicketInfo).build();
    }

    public static List<Ticket> listTicketResponse(byte[] bytes) {
        RPCProto.TicketResponse ticketResponse = null;
        try {
            ticketResponse = RPCProto.TicketResponse.parseFrom(bytes);
            List<RPCProto.Ticket> ticketList = ticketResponse.getListTicketResult().getTicketList();
            List<Ticket> tickets = new ArrayList<>();
            for (RPCProto.Ticket ticket : ticketList) {
                Ticket ticket1 = new Ticket(ticket.getId(), ticket.getMovieId(), ticket.getFilmSessions(),
                        LocalDateTime.parse(ticket.getStartTime()), ticket.getOptionalSeats(), ticket.getSelectedSeats(),
                        ticket.getLeftTicket(), ticket.getStatus());
                tickets.add(ticket1);
            }
            return tickets;
        } catch (InvalidProtocolBufferException e) {
            throw new RPCException(e);
        }
    }

    public static RPCProto.TicketRequest deleteTicketRequest(long id) {
        RPCProto.TicketRequest.DeleteTicketInfo deleteTicketInfo = RPCProto.TicketRequest.DeleteTicketInfo.newBuilder()
                .setId(id).build();
        return RPCProto.TicketRequest.newBuilder().setDeleteTicketInfo(deleteTicketInfo).build();
    }

    public static RPCProto.TicketRequest updateTicketRequest(Ticket ticket) {
        RPCProto.Ticket ticket1 = RPCProto.Ticket.newBuilder()
                .setId(ticket.getId())
                .setMovieId(ticket.getMovieId())
                .setFilmSessions(ticket.getFilmSessions())
                .setStartTime(ticket.getStartTime().toString())
                .setOptionalSeats(ticket.getOptionalSeats())
                .setSelectedSeats(ticket.getSelectedSeats())
                .setLeftTicket(ticket.getLeftTicket())
                .setStatus(ticket.getStatus()).build();
        RPCProto.TicketRequest.UpdateTicketInfo updateTicketInfo = RPCProto.TicketRequest.UpdateTicketInfo.newBuilder()
                .setTicket(ticket1).build();
        return RPCProto.TicketRequest.newBuilder().setUpdateTicketInfo(updateTicketInfo).build();
    }

    public static RPCProto.TicketRequest updateSeatRequest(Seat seat) {
        RPCProto.Seat seat1 = RPCProto.Seat.newBuilder()
                .setSeat(seat.getSeat())
                .setTicketId(seat.getTicketId())
                .setOperate(seat.getOperate()).build();

        RPCProto.TicketRequest.UpdateSeatInfo updateSeatInfo = RPCProto.TicketRequest.UpdateSeatInfo.newBuilder()
                .setSeat(seat1).build();
        return RPCProto.TicketRequest.newBuilder().setUpdateSeatInfo(updateSeatInfo).build();
    }

}
