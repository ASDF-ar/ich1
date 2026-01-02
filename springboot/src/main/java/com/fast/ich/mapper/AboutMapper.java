package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.About;

/**
 * 关于我们Mapper接口
 *
 * @author fast
 * @date 2025-11-07
 */
@Mapper
public interface AboutMapper
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
     * 修改关于我们
     *
     * @param about 关于我们
     * @return 结果
     */
    public int updateAbout(About about);

    /**
     * 删除关于我们
     *
     * @param aboutId 关于我们主键
     * @return 结果
     */
    public int deleteAboutByAboutId(String aboutId);

    /**
     * 批量删除关于我们
     *
     * @param aboutIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAboutByAboutIds(String[] aboutIds);
}
