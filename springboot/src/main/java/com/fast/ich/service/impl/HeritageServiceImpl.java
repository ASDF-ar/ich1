package com.fast.ich.service.impl;

import java.util.List;

import com.fast.ich.domain.vo.PieVO;
import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.HeritageMapper;
import com.fast.ich.domain.Heritage;
import com.fast.ich.service.IHeritageService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 非遗项目Service业务层处理
 *
 * @author fast
 * @date 2025-12-24
 */
@Service
public class HeritageServiceImpl implements IHeritageService
{
    @Autowired
    private HeritageMapper heritageMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询非遗项目
     *
     * @param heritageId 非遗项目主键
     * @return 非遗项目
     */
    @Override
    public Heritage selectHeritageByHeritageId(String heritageId)
    {
        return heritageMapper.selectHeritageByHeritageId(heritageId);
    }

    /**
     * 查询非遗项目列表
     *
     * @param heritage 非遗项目
     * @return 非遗项目
     */
    @Override
    public List<Heritage> selectHeritageList(Heritage heritage)
    {
        return heritageMapper.selectHeritageList(heritage);
    }

    /**
     * 新增非遗项目
     *
     * @param heritage 非遗项目
     * @return 结果
     */
    @Override
    public int insertHeritage(Heritage heritage)
    {
        heritage.setCreateTime(DateUtils.getNowDate());
        //生成一个UUID插入非遗项目对象中
        heritage.setHeritageId(IdUtils.fastSimpleUUID());
        return heritageMapper.insertHeritage(heritage);
    }

    /**
     * 批量新增非遗项目
     *
     * @param heritages 非遗项目List
     * @return 结果
     */
    @Override
    public int batchInsertHeritage(List<Heritage> heritages)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(heritages)) {
            try {
                for (int i = 0; i < heritages.size(); i++) {
                    int row = heritageMapper.insertHeritage(heritages.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == heritages.size() - 1;
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
     * 修改非遗项目
     *
     * @param heritage 非遗项目
     * @return 结果
     */
    @Override
    public int updateHeritage(Heritage heritage)
    {
        return heritageMapper.updateHeritage(heritage);
    }

    /**
     * 批量删除非遗项目
     *
     * @param heritageIds 需要删除的非遗项目主键
     * @return 结果
     */
    @Override
    public int deleteHeritageByHeritageIds(String[] heritageIds)
    {
        return heritageMapper.deleteHeritageByHeritageIds(heritageIds);
    }

    /**
     * 删除非遗项目信息
     *
     * @param heritageId 非遗项目主键
     * @return 结果
     */
    @Override
    public int deleteHeritageByHeritageId(String heritageId)
    {
        return heritageMapper.deleteHeritageByHeritageId(heritageId);
    }

    /**
     * 提取唯一分类字段并返回给前端
     * @return
     */
    @Override
    public List<String> selectOnlyCategories() {

        return heritageMapper.selectOnlyCategories();
    }

    /**
     * 查询每种项目分类各有多少个
     * @return
     */
    @Override
    public List<PieVO> selectCategoryChart() {
        return heritageMapper.selectCategoryChart();
    }
}
