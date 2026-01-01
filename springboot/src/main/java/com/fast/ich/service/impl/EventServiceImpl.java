package com.fast.ich.service.impl;

import java.util.List;

import com.fast.ich.domain.vo.EventVO;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.EventMapper;
import com.fast.ich.domain.Event;
import com.fast.ich.service.IEventService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 活动预告Service业务层处理
 *
 * @author fast
 * @date 2025-11-05
 */
@Service
public class EventServiceImpl implements IEventService
{
    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询活动预告
     *
     * @param eventId 活动预告主键
     * @return 活动预告
     */
    @Override
    public Event selectEventByEventId(String eventId)
    {
        return eventMapper.selectEventByEventId(eventId);
    }

    /**
     * 查询活动预告列表
     *
     * @param event 活动预告
     * @return 活动预告
     */
    @Override
    public List<Event> selectEventList(Event event)
    {
        return eventMapper.selectEventList(event);
    }

    /**
     * 新增活动预告
     *
     * @param event 活动预告
     * @return 结果
     */
    @Override
    public int insertEvent(Event event)
    {
        //生成一个UUID作为活动预告ID
       IdUtils IdUtil;
        event.setEventId(IdUtils.fastSimpleUUID());
        return eventMapper.insertEvent(event);
    }

    /**
     * 批量新增活动预告
     *
     * @param events 活动预告List
     * @return 结果
     */
    @Override
    public int batchInsertEvent(List<Event> events)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(events)) {
            try {
                for (int i = 0; i < events.size(); i++) {
                    int row = eventMapper.insertEvent(events.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == events.size() - 1;
                    if (bool){
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            }finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改活动预告
     *
     * @param event 活动预告
     * @return 结果
     */
    @Override
    public int updateEvent(Event event)
    {
        return eventMapper.updateEvent(event);
    }

    /**
     * 批量删除活动预告
     *
     * @param eventIds 需要删除的活动预告主键
     * @return 结果
     */
    @Override
    public int deleteEventByEventIds(String[] eventIds)
    {
        return eventMapper.deleteEventByEventIds(eventIds);
    }

    /**
     * 删除活动预告信息
     *
     * @param eventId 活动预告主键
     * @return 结果
     */
    @Override
    public int deleteEventByEventId(String eventId)
    {
        return eventMapper.deleteEventByEventId(eventId);
    }

    /**
     * 查询所有大于当前日期的活动预告列表, 并根据日期正序排序
     * @return
     */
    @Override
    public List<EventVO> selectGreaterThanTheCurrentDateEventList() {
        return eventMapper.selectGreaterThanTheCurrentDateEventList();
    }
}
