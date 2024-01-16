package com.lzb.www.service.impl;

import com.lzb.www.dao.OrderDao;
import com.lzb.www.dao.TicketDao;
import com.lzb.www.pojo.po.Order;
import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.pojo.vo.Seat;
import com.lzb.www.service.TicketService;
import com.lzb.www.util.StringUtils;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.lzb.www.constant.GlobalConstant.*;

@Hosted
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private OrderDao orderDao;
    private static final int OPTIONAL_SEATS_ADD_ONE = 1;
    private static final int THE_FIRST_SCENE = 9;
    private static final int THE_SECOND_SCENE = 14;
    private static final int THE_THIRD_SCENE = 16;
    private static final int THE_FOURTH_SCENE = 20;

    @Override
    public boolean insertTicket(Ticket ticket, int movieId) {
        LocalDateTime now = LocalDateTime.now();
        // 获取当前时间为ticket id
        String data = now.format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        Long ticketId = Long.valueOf(data);
        String optionalSeats = ticket.getOptionalSeats().replace(" ", "");
        ticket.setId(ticketId);
        ticket.setMovieId(movieId);
        selectStartTime(ticket);
        ticket.setLeftTicket(StringUtils.commaCount(optionalSeats) + OPTIONAL_SEATS_ADD_ONE);
        ticket.setStatus(EXIST);
        Order order = new Order(null, ticketId, DEFAULT_USER_ID, DEFAULT_SEAT, DEFAULT_PAYMENT_METHOD,
                DEFAULT_PRICE, now, NOT_PURCHASED);
        String[] optionalSeatsArray = optionalSeats.substring(1, optionalSeats.length() - 1).split(",");
        // 添加场次时 同时生成对应的订单
        for (String seat : optionalSeatsArray) {
            order.setSeat(Integer.parseInt(seat));
            if (!orderDao.insertOrder(order)) {
                return false;
            }
        }
        return ticketDao.insertTicket(ticket);
    }

    @Override
    public List<Object> listTicket(int movieId) {
        return ticketDao.listTicket(movieId);
    }

    @Override
    public boolean deleteTicket(Long id) {
        return ticketDao.updateStatus(id, 1);
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        // 已被购买不可修改
        if (!"[]".equals(ticket.getSelectedSeats())) {
            return false;
        }
        selectStartTime(ticket);

        String[] newOptionalSeatsArray = ticket.getOptionalSeats().replace(" ", "")
                .substring(1, ticket.getOptionalSeats().length() - 1).split(",");

        int[] array = new int[newOptionalSeatsArray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(newOptionalSeatsArray[i]);
        }
        Arrays.sort(array);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                stringBuilder.append(array[i]).append("]");
            } else {
                stringBuilder.append(array[i]).append(",");
            }
        }
        ticket.setOptionalSeats(String.valueOf(stringBuilder));
        ticket.setLeftTicket(newOptionalSeatsArray.length);

        Ticket daoTicket = ticketDao.getTicket(ticket.getId());
        String oldOptionalSeats = daoTicket.getOptionalSeats().replace(" ", "");
        if (!oldOptionalSeats.equals(ticket.getOptionalSeats())) {
            // 修改了座位
            String[] oldOptionalSeatsArray = oldOptionalSeats.substring(1, oldOptionalSeats.length() - 1).split(",");

            Map<String, Integer> map = new HashMap<>();
            for (String oldSeat : oldOptionalSeatsArray) {
                map.put(oldSeat, 1);
            }
            for (String newSeat : newOptionalSeatsArray) {
                Integer value = map.get(newSeat);
                if (value != null) {
                    // 已存在 存入 2
                    map.put(newSeat, ++value);
                    continue;
                }
                // 不存在  存入0
                map.put(newSeat, 0);
            }
            // 0 新的   1 删除的   2  不动的
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 1) {
                    Order order = orderDao.getOrder(ticket.getId(), Integer.parseInt(entry.getKey()));
                    order.setStatus(ABANDONED_SEATS);
                    orderDao.updateOrder(order);
                }
                if (entry.getValue() == 0) {
                    Order order = new Order(null, ticket.getId(), DEFAULT_USER_ID, Integer.parseInt(entry.getKey()),
                            DEFAULT_PAYMENT_METHOD, DEFAULT_PRICE, LocalDateTime.now(), NOT_PURCHASED);
                    orderDao.insertOrder(order);
                }
            }
        }
        return ticketDao.updateTicket(ticket);
    }

    @Override
    public boolean updateSeat(Seat seat) {
        Order order = orderDao.getOrder(seat.getTicketId(), seat.getSeat());
        Integer operate = seat.getOperate();
        if (operate == ADD_SEATS) {
            // 增加座位
            if (order != null && order.getStatus() == NOT_PURCHASED) {
                return false;
            }
            // 修改ticket
            Ticket ticket = ticketDao.getTicket(seat.getTicketId());
            String oldOptionalSeats = ticket.getOptionalSeats().replace(" ", "");
            String[] oldOptionalSeatsArray = oldOptionalSeats.substring(1, oldOptionalSeats.length() - 1).split(",");
            List<Integer> newOptionalSeats = new ArrayList<>();
            boolean flag = true;
            for (int i = 0; i < oldOptionalSeatsArray.length; i++) {
                // 目前该次被选座位小于数组值, 填入 如   1  2  3  5  6  插入4  当4 > 3 不插入  当4 < 5 插入
                if (flag && seat.getSeat() < Integer.parseInt(oldOptionalSeatsArray[i])) {
                    newOptionalSeats.add(seat.getSeat());
                    flag = false;
                }
                newOptionalSeats.add(Integer.parseInt(oldOptionalSeatsArray[i]));
                // 如果已经到最后但还未插入, 插入最后
                if (flag && i == oldOptionalSeatsArray.length - 1) {
                    newOptionalSeats.add(seat.getSeat());
                }
            }
            ticket.setLeftTicket(ticket.getLeftTicket() + 1);
            ticket.setOptionalSeats(newOptionalSeats.toString());
            return orderDao.insertOrder(new Order(null, seat.getTicketId(), -1, seat.getSeat(),
                    "0", 1.0, LocalDateTime.now(), 5)) && ticketDao.updateTicket(ticket);
        } else if (operate == 1) {
            // 删除座位
            if (order == null) {
                return false;
            }
            Integer status = order.getStatus();
            Ticket ticket = ticketDao.getTicket(seat.getTicketId());
            if (status == 0) {
                // 已被买, 退票
                order.setStatus(3);
                String oldSelectedSeats = ticket.getSelectedSeats().replace(" ", "");
                String[] oldSelectedSeatsArr = oldSelectedSeats.substring(1, oldSelectedSeats.length() - 1).split(",");
                Object[] newSelectedSeatsStream = Arrays.stream(oldSelectedSeatsArr)
                        .filter(tempSeat -> Integer.parseInt(tempSeat) != seat.getSeat()).toArray();
                ticket.setSelectedSeats(Arrays.toString(newSelectedSeatsStream));
            } else {
                // 未被买, 弃票
                order.setStatus(6);
                String oldOptionalSeats = ticket.getOptionalSeats().replace(" ", "");
                String[] oldOptionalSeatsArr = oldOptionalSeats.substring(1, oldOptionalSeats.length() - 1).split(",");
                Object[] newOptionalSeatsStream = Arrays.stream(oldOptionalSeatsArr).filter(tempSeat -> Integer.parseInt(tempSeat) != seat.getSeat()).toArray();
                String newOptionalSeats = Arrays.toString(newOptionalSeatsStream);
                ticket.setOptionalSeats(newOptionalSeats);
                ticket.setLeftTicket(ticket.getLeftTicket() - 1);
            }
            return orderDao.updateOrder(order) && ticketDao.updateTicket(ticket);
        }
        return false;
    }


    private void selectStartTime(Ticket ticket) {
        String filmSessions = ticket.getFilmSessions();
        LocalDateTime startTime = ticket.getStartTime();
        if (STRING_ONE.equals(filmSessions)) {
            ticket.setStartTime(LocalDateTime.of(LocalDate.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth())
                    , LocalTime.of(THE_FIRST_SCENE, INT_ZERO, INT_ZERO)));
        } else if (STRING_TWO.equals(filmSessions)) {
            ticket.setStartTime(LocalDateTime.of(LocalDate.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth()),
                    LocalTime.of(THE_SECOND_SCENE, INT_ZERO, INT_ZERO)));
        } else if (STRING_THREE.equals(filmSessions)) {
            ticket.setStartTime(LocalDateTime.of(LocalDate.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth()),
                    LocalTime.of(THE_THIRD_SCENE, INT_ZERO, INT_ZERO)));
        } else if (STRING_FOUR.equals(filmSessions)) {
            ticket.setStartTime(LocalDateTime.of(LocalDate.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth()),
                    LocalTime.of(THE_FOURTH_SCENE, INT_ZERO, INT_ZERO)));
        }
    }

}
