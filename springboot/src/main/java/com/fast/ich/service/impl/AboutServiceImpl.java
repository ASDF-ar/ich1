package com.fast.ich.service.impl;

import java.util.List;

import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.AboutMapper;
import com.fast.ich.domain.About;
import com.fast.ich.service.IAboutService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 关于我们Service业务层处理
 *
 * @author fast
 * @date 2025-11-07
 */
@Service
public class AboutServiceImpl implements IAboutService
{
    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询关于我们
     *
     * @param aboutId 关于我们主键
     * @return 关于我们
     */
    @Override
    public About selectAboutByAboutId(String aboutId)
    {
        return aboutMapper.selectAboutByAboutId(aboutId);
    }

    /**
     * 查询关于我们列表
     *
     * @param about 关于我们
     * @return 关于我们
     */
    @Override
    public List<About> selectAboutList(About about)
    {
        return aboutMapper.selectAboutList(about);
    }

    /**
     * 新增关于我们
     *
     * @param about 关于我们
     * @return 结果
     */
    @Override
    public int insertAbout(About about)
    {
        about.setAboutId(IdUtils.fastSimpleUUID());
        return aboutMapper.insertAbout(about);
    }

    /**
     * 批量新增关于我们
     *
     * @param abouts 关于我们List
     * @return 结果
     */
    @Override
    public int batchInsertAbout(List<About> abouts)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(abouts)) {
            try {
                for (int i = 0; i < abouts.size(); i++) {
                    int row = aboutMapper.insertAbout(abouts.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == abouts.size() - 1;
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
     * 修改关于我们
     *
     * @param about 关于我们
     * @return 结果
     */
    @Override
    public int updateAbout(About about)
    {
        return aboutMapper.updateAbout(about);
    }

    /**
     * 批量删除关于我们
     *
     * @param aboutIds 需要删除的关于我们主键
     * @return 结果
     */
    @Override
    public int deleteAboutByAboutIds(String[] aboutIds)
    {
        return aboutMapper.deleteAboutByAboutIds(aboutIds);
    }

    /**
     * 删除关于我们信息
     *
     * @param aboutId 关于我们主键
     * @return 结果
     */
    @Override
    public int deleteAboutByAboutId(String aboutId)
    {
        return aboutMapper.deleteAboutByAboutId(aboutId);
    }
}
