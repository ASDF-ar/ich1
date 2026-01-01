package com.fast.ich.mapper;

import java.util.List;

import com.fast.ich.domain.vo.EventVO;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.Event;

/**
 * 活动预告Mapper接口
 *
 * @author fast
 * @date 2025-11-05
 */
@Mapper
public interface EventMapper
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
     * 修改活动预告
     *
     * @param event 活动预告
     * @return 结果
     */
    public int updateEvent(Event event);

    /**
     * 删除活动预告
     *
     * @param eventId 活动预告主键
     * @return 结果
     */
    public int deleteEventByEventId(String eventId);

    /**
     * 批量删除活动预告
     *
     * @param eventIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEventByEventIds(String[] eventIds);

    /**
     * 查询所有大于当前日期的活动预告列表, 并根据日期正序排序
     * @return
     */
    List<EventVO> selectGreaterThanTheCurrentDateEventList();
}
