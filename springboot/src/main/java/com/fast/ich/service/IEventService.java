package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Event;
import com.fast.ich.domain.vo.EventVO;

/**
 * 活动预告Service接口
 *
 * @author fast
 * @date 2025-11-05
 */
public interface IEventService
{
    /**
     * 查询活动预告
     *
     * @param eventId 活动预告主键
     * @return 活动预告
     */
    public Event selectEventByEventId(String eventId);

    /**
     * 查询活动预告列表
     *
     * @param event 活动预告
     * @return 活动预告集合
     */
    public List<Event> selectEventList(Event event);

    /**
     * 新增活动预告
     *
     * @param event 活动预告
     * @return 结果
     */
    public int insertEvent(Event event);

    /**
     * 批量新增活动预告
     *
     * @param events 活动预告List
     * @return 结果
     */
    public int batchInsertEvent(List<Event> events);

    /**
     * 修改活动预告
     *
     * @param event 活动预告
     * @return 结果
     */
    public int updateEvent(Event event);

    /**
     * 批量删除活动预告
     *
     * @param eventIds 需要删除的活动预告主键集合
     * @return 结果
     */
    public int deleteEventByEventIds(String[] eventIds);

    /**
     * 删除活动预告信息
     *
     * @param eventId 活动预告主键
     * @return 结果
     */
    public int deleteEventByEventId(String eventId);
    /**
     * 查询所有大于当前日期的活动预告列表, 并根据日期正序排序
     * @return
     */
    List<EventVO> selectGreaterThanTheCurrentDateEventList();                       
}
