package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Banner;

/**
 * 轮播图Service接口
 *
 * @author fast
 * @date 2025-11-07
 */
public interface IBannerService
{
    /**
     * 查询轮播图
     *
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    public Banner selectBannerByBannerId(String bannerId);

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 批量新增轮播图
     *
     * @param banners 轮播图List
     * @return 结果
     */
    public int batchInsertBanner(List<Banner> banners);

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 批量删除轮播图
     *
     * @param bannerIds 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteBannerByBannerIds(String[] bannerIds);

    /**
     * 删除轮播图信息
     *
     * @param bannerId 轮播图主键
     * @return 结果
     */
    public int deleteBannerByBannerId(String bannerId);
}
