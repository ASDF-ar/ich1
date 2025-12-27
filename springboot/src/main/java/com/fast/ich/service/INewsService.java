package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.News;

/**
 * 新闻资讯Service接口
 *
 * @author fast
 * @date 2025-12-27
 */
public interface INewsService
{
    /**
     * 查询新闻资讯
     *
     * @param newsId 新闻资讯主键
     * @return 新闻资讯
     */
    public News selectNewsByNewsId(String newsId);

    /**
     * 查询新闻资讯列表
     *
     * @param news 新闻资讯
     * @return 新闻资讯集合
     */
    public List<News> selectNewsList(News news);

    /**
     * 新增新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    public int insertNews(News news);

    /**
     * 批量新增新闻资讯
     *
     * @param newss 新闻资讯List
     * @return 结果
     */
    public int batchInsertNews(List<News> newss);

    /**
     * 修改新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    public int updateNews(News news);

    /**
     * 批量删除新闻资讯
     *
     * @param newsIds 需要删除的新闻资讯主键集合
     * @return 结果
     */
    public int deleteNewsByNewsIds(String[] newsIds);

    /**
     * 删除新闻资讯信息
     *
     * @param newsId 新闻资讯主键
     * @return 结果
     */
    public int deleteNewsByNewsId(String newsId);
}
