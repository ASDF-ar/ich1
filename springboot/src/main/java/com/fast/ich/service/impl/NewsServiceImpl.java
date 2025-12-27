package com.fast.ich.service.impl;

import java.util.List;
import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.NewsMapper;
import com.fast.ich.domain.News;
import com.fast.ich.service.INewsService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 新闻资讯Service业务层处理
 *
 * @author fast
 * @date 2025-12-27
 */
@Service
public class NewsServiceImpl implements INewsService
{
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询新闻资讯
     *
     * @param newsId 新闻资讯主键
     * @return 新闻资讯
     */
    @Override
    public News selectNewsByNewsId(String newsId)
    {
        return newsMapper.selectNewsByNewsId(newsId);
    }

    /**
     * 查询新闻资讯列表
     *
     * @param news 新闻资讯
     * @return 新闻资讯
     */
    @Override
    public List<News> selectNewsList(News news)
    {
        return newsMapper.selectNewsList(news);
    }

    /**
     * 新增新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    @Override
    public int insertNews(News news)
    {
        news.setCreateTime(DateUtils.getNowDate());
        news.setNewsId(IdUtils.fastSimpleUUID());
        return newsMapper.insertNews(news);
    }

    /**
     * 批量新增新闻资讯
     *
     * @param newss 新闻资讯List
     * @return 结果
     */
    @Override
    public int batchInsertNews(List<News> newss)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(newss)) {
            try {
                for (int i = 0; i < newss.size(); i++) {
                    int row = newsMapper.insertNews(newss.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == newss.size() - 1;
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
     * 修改新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    @Override
    public int updateNews(News news)
    {
        return newsMapper.updateNews(news);
    }

    /**
     * 批量删除新闻资讯
     *
     * @param newsIds 需要删除的新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteNewsByNewsIds(String[] newsIds)
    {
        return newsMapper.deleteNewsByNewsIds(newsIds);
    }

    /**
     * 删除新闻资讯信息
     *
     * @param newsId 新闻资讯主键
     * @return 结果
     */
    @Override
    public int deleteNewsByNewsId(String newsId)
    {
        return newsMapper.deleteNewsByNewsId(newsId);
    }
}
