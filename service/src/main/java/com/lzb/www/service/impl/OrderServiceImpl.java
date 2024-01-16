package com.lzb.www.service.impl;

import com.lzb.www.dao.MovieDao;
import com.lzb.www.dao.OrderDao;
import com.lzb.www.dao.TicketDao;
import com.lzb.www.pojo.po.Movie;
import com.lzb.www.pojo.po.Order;
import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.pojo.vo.Orders;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.service.OrderService;
import rpc.util.RedisLock;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;

import java.time.LocalDateTime;
import java.util.*;

import static com.lzb.www.constant.GlobalConstant.*;

@Hosted
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private MovieDao movieDao;
    private static final String VIP = "0";
    private static final double DISCOUNT = 0.8;
    private static final int MIN_TICKET_COUNT = 1;
    private final Map<Long, Movie> movieMap = new HashMap<>();


    @Override
    public boolean insertOrder(Order order) {
        // 生成订单
        Long ticketId = order.getTicketId();
        Ticket ticket = ticketDao.getTicket(ticketId);
        if (ticket == null) {
            return false;
        }
        if (ticket.getLeftTicket() < MIN_TICKET_COUNT) {
            return false;
        }
        // 会员打八折
        if (VIP.equals(order.getPaymentMethod())) {
            order.setPrice(order.getPrice() * DISCOUNT);
        }
        // 设置 order 过期时间
        LocalDateTime startTime = ticket.getStartTime();
        Movie movie;
        // 从缓存中取movie, 为空查询数据库
        if (movieMap.get(ticketId) == null) {
            movie = movieDao.getMovie(ticket.getMovieId());
            movieMap.put(ticketId, movie);
        } else {
            movie = movieMap.get(ticketId);
        }
        Integer theTimeOfMovie = movie.getTheTimeOfMovie();
        order.setExpirationTime(startTime.plusMinutes(theTimeOfMovie));

        order.setStatus(NOT_WATCHED);
        Order daoOrder = orderDao.getOrder(ticketId, order.getSeat());
        // 获取状态, 已被购买返回false
        if (daoOrder.getStatus() != NOT_PURCHASED) {
            return false;
        }
        // 生成锁的值
        String lockValue = UUID.randomUUID().toString();
        // 阻塞式获取锁  锁住ticket 同一场次同时只有一个人能操作, 防止同一时间修改ticket中的 可选座位和已选座位 时出现并发问题
        if (!RedisLock.getLock(ticketId, lockValue)) {
            return false;
        }
        try {
            // 购票利用update sql 的原子性 实现乐观锁
            if (orderDao.updateOrder(order)) {
                updateTicket(ticketId, order.getSeat());
                return true;
            }
            return false;
        } finally {
            RedisLock.releaseLock(ticketId, lockValue);
        }
    }

    private void updateTicket(Long ticketId, int targetSeat) {
        // 再获取一次避免并发问题
        Ticket ticket = ticketDao.getTicket(ticketId);
        // 获得旧的可选座位数组
        String oldOptionalSeats = ticket.getOptionalSeats().replace(" ", "");
        String[] oldOptionalSeatsArr = oldOptionalSeats.substring(1, oldOptionalSeats.length() - 1).split(",");
        // 通过stream流去除已被选择的座位
        Object[] newOptionalSeatsStream = Arrays.stream(oldOptionalSeatsArr).filter(seat -> Integer.parseInt(seat) != targetSeat).toArray();
        String newOptionalSeats = Arrays.toString(newOptionalSeatsStream);
        ticket.setOptionalSeats(newOptionalSeats);
        // 获得旧的已选座位
        String oldSelectedSeats = ticket.getSelectedSeats().replace(" ", "");
        if ("[]".equals(oldSelectedSeats)) {
            // 为空直接填入
            ticket.setSelectedSeats("[" + targetSeat + "]");
        } else if (!oldSelectedSeats.contains(",")) {
            // 只有一个元素
            int seat = Integer.parseInt(oldSelectedSeats.substring(1, oldSelectedSeats.length() - 1));
            // 有两个元素 比较后填入
            if (seat < targetSeat) {
                ticket.setSelectedSeats("[" + seat + "," + targetSeat + "]");
            } else {
                ticket.setSelectedSeats("[" + targetSeat + "," + seat + "]");
            }
        } else {
            // 有多个元素, 先获得已选座位数组
            String[] oldSelectedSeatsArr = oldSelectedSeats.substring(1, oldSelectedSeats.length() - 1).split(",");
            List<Integer> newSelectedSeats = new ArrayList<>();
            boolean flag = true;
            // 遍历数组
            for (int i = 0; i < oldSelectedSeatsArr.length; i++) {
                // 目前该次被选座位小于数组值, 填入 如   1  2  3  5  6  插入4  当4 > 3 不插入  当4 < 5 插入
                if (flag && targetSeat < Integer.parseInt(oldSelectedSeatsArr[i])) {
                    newSelectedSeats.add(targetSeat);
                    flag = false;
                }
                newSelectedSeats.add(Integer.parseInt(oldSelectedSeatsArr[i]));
                // 如果已经到最后但还未插入, 插入最后
                if (flag && i == oldSelectedSeatsArr.length - 1) {
                    newSelectedSeats.add(targetSeat);
                }
            }
            ticket.setSelectedSeats(newSelectedSeats.toString());
        }
        ticket.setLeftTicket(ticket.getLeftTicket() - 1);
        ticketDao.updateTicket(ticket);
    }

    @Override
    public PageBean<Object> listOrders(int currentPage, int pageSize, int id) {
        int begin = (currentPage - INT_ONE) * pageSize;
        List<Object> ordersList = new ArrayList<>();
        List<Object> rows = orderDao.listOrders(begin, pageSize, id);
        rows.forEach(row -> {
            LocalDateTime expirationTime = ((Order) row).getExpirationTime();
            // 判断是否过期
            if (expirationTime.isBefore(LocalDateTime.now())) {
                // 过期则修改状态
                ((Order) row).setStatus(2);
                orderDao.update((Order) row);
            }
            Long ticketId = ((Order) row).getTicketId();
            // 封装信息返回前端
            Ticket ticket = ticketDao.getTicket(ticketId);
            Integer movieId = ticket.getMovieId();
            Movie movie = movieDao.getMovie(movieId);
            Orders orders = new Orders((Order) row, movie.getName(), ticket.getStartTime());
            ordersList.add(orders);
        });
        Number count = orderDao.countOrders(id);
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setRows(ordersList);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    public boolean returnTicket(int id) {
        Order order = orderDao.getOrder(id);
        order.setStatus(3);
        // 保留退票记录, 生成新票
        Order newOrder = new Order(null, order.getTicketId(), DEFAULT_USER_ID, order.getSeat(),
                DEFAULT_PAYMENT_METHOD, DEFAULT_PRICE, LocalDateTime.now(), NOT_PURCHASED);
        return orderDao.update(order) && orderDao.insertOrder(newOrder);
    }

    @Override
    public boolean deleteOrder(int id) {
        Order order = orderDao.getOrder(id);
        order.setStatus(DO_NOT_DISPLAY);
        return orderDao.update(order);
    }

}
