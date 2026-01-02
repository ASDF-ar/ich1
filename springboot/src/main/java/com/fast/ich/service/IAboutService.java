package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.About;

/**
 * 关于我们Service接口
 *
 * @author fast
 * @date 2025-11-07
 */
public interface IAboutService
{
    /**
     * 查询关于我们
     *
     * @param aboutId 关于我们主键
     * @return 关于我们
     */
    public About selectAboutByAboutId(String aboutId);

    /**
     * 查询关于我们列表
     *
     * @param about 关于我们
     * @return 关于我们集合
     */
    public List<About> selectAboutList(About about);

    /**
     * 新增关于我们
     *
     * @param about 关于我们
     * @return 结果
     */
    public int insertAbout(About about);

    /**
     * 批量新增关于我们
     *
     * @param abouts 关于我们List
     * @return 结果
     */
    public int batchInsertAbout(List<About> abouts);

    /**
     * 修改关于我们
     *
     * @param about 关于我们
     * @return 结果
     */
    public int updateAbout(About about);

    /**
     * 批量删除关于我们
     *
     * @param aboutIds 需要删除的关于我们主键集合
     * @return 结果
     */
    public int deleteAboutByAboutIds(String[] aboutIds);

    /**
     * 删除关于我们信息
     *
     * @param aboutId 关于我们主键
     * @return 结果
     */
    public int deleteAboutByAboutId(String aboutId);
}
