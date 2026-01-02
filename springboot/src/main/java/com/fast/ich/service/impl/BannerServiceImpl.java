package com.fast.ich.service.impl;

import java.util.List;

import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.BannerMapper;
import com.fast.ich.domain.Banner;
import com.fast.ich.service.IBannerService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 轮播图Service业务层处理
 *
 * @author fast
 * @date 2025-11-07
 */
@Service
public class BannerServiceImpl implements IBannerService
{
    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询轮播图
     *
     * @param bannerId 轮播图主键
     * @return 轮播图
     */
    @Override
    public Banner selectBannerByBannerId(String bannerId)
    {
        return bannerMapper.selectBannerByBannerId(bannerId);
    }

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {

        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setBannerId(IdUtils.fastSimpleUUID());
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 批量新增轮播图
     *
     * @param banners 轮播图List
     * @return 结果
     */
    @Override
    public int batchInsertBanner(List<Banner> banners)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(banners)) {
            try {
                for (int i = 0; i < banners.size(); i++) {
                    int row = bannerMapper.insertBanner(banners.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == banners.size() - 1;
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
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除轮播图
     *
     * @param bannerIds 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByBannerIds(String[] bannerIds)
    {
        return bannerMapper.deleteBannerByBannerIds(bannerIds);
    }

    /**
     * 删除轮播图信息
     *
     * @param bannerId 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByBannerId(String bannerId)
    {
        return bannerMapper.deleteBannerByBannerId(bannerId);
    }
}
