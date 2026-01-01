package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.News;

/**
 * 新闻资讯Mapper接口
 *
 * @author fast
 * @date 2025-12-27
 */
@Mapper
public interface NewsMapper
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
     * 修改新闻资讯
     *
     * @param news 新闻资讯
     * @return 结果
     */
    public int updateNews(News news);

    /**
     * 删除新闻资讯
     *
     * @param newsId 新闻资讯主键
     * @return 结果
     */
    public int deleteNewsByNewsId(String newsId);

    /**
     * 批量删除新闻资讯
     *
     * @param newsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewsByNewsIds(String[] newsIds);

    /**
     * 查询新闻资讯表中是否已存在焦点新闻
     * @return
     */
    Boolean selectIsFocus();


    /**
     * 查询新闻资讯表中此前的焦点新闻的新闻ID
     * @return
     */
    String selectOldFocusNewsToId();

    /**
     * 查询焦点新闻信息
     * @return
     */
    News selectFocusNews();

    List<News> selectNonFocusNewsList();
}
