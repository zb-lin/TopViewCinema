package com.lzb.www.service;

import com.lzb.www.pojo.po.Ticket;
import com.lzb.www.pojo.vo.Seat;

import java.util.List;

public interface TicketService {
    /**
     * 添加新的场次
     *
     * @param ticket 添加的场次对象
     * @param id     电影id
     * @return 是否添加成功
     */
    boolean insertTicket(Ticket ticket, int id);

    /**
     * 根据电影id查询场次
     *
     * @param id 电影id
     * @return 场次集合
     */
    List<Object> listTicket(int id);

    /**
     * 删除某一场次
     *
     * @param id 场次id
     * @return 是否删除成功
     */
    boolean deleteTicket(Long id);

    /**
     * 更新场次信息
     *
     * @param ticket 更新的场次对象
     * @return 是否更新成功
     */
    boolean updateTicket(Ticket ticket);

    /**
     * 修改座位信息
     *
     * @param seat 修改的座位信息
     * @return 是否更新成功
     */
    boolean updateSeat(Seat seat);
}
