package com.fast.ich.service.impl;

import java.util.List;

import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.ProjectCategoryMapper;
import com.fast.ich.domain.ProjectCategory;
import com.fast.ich.service.IProjectCategoryService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 项目申报类别Service业务层处理
 *
 * @author fast
 * @date 2025-11-05
 */
@Service
public class ProjectCategoryServiceImpl implements IProjectCategoryService
{
    @Autowired
    private ProjectCategoryMapper projectCategoryMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询项目申报类别
     *
     * @param pcId 项目申报类别主键
     * @return 项目申报类别
     */
    @Override
    public ProjectCategory selectProjectCategoryByPcId(String pcId)
    {
        return projectCategoryMapper.selectProjectCategoryByPcId(pcId);
    }

    /**
     * 查询项目申报类别列表
     *
     * @param projectCategory 项目申报类别
     * @return 项目申报类别
     */
    @Override
    public List<ProjectCategory> selectProjectCategoryList(ProjectCategory projectCategory)
    {
        return projectCategoryMapper.selectProjectCategoryList(projectCategory);
    }

    /**
     * 新增项目申报类别
     *
     * @param projectCategory 项目申报类别
     * @return 结果
     */
    @Override
    public int insertProjectCategory(ProjectCategory projectCategory)
    {
        projectCategory.setPcId(IdUtils.fastSimpleUUID());
        return projectCategoryMapper.insertProjectCategory(projectCategory);
    }

    /**
     * 批量新增项目申报类别
     *
     * @param projectCategorys 项目申报类别List
     * @return 结果
     */
    @Override
    public int batchInsertProjectCategory(List<ProjectCategory> projectCategorys)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(projectCategorys)) {
            try {
                for (int i = 0; i < projectCategorys.size(); i++) {
                    int row = projectCategoryMapper.insertProjectCategory(projectCategorys.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == projectCategorys.size() - 1;
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
     * 修改项目申报类别
     *
     * @param projectCategory 项目申报类别
     * @return 结果
     */
    @Override
    public int updateProjectCategory(ProjectCategory projectCategory)
    {
        return projectCategoryMapper.updateProjectCategory(projectCategory);
    }

    /**
     * 批量删除项目申报类别
     *
     * @param pcIds 需要删除的项目申报类别主键
     * @return 结果
     */
    @Override
    public int deleteProjectCategoryByPcIds(String[] pcIds)
    {
        return projectCategoryMapper.deleteProjectCategoryByPcIds(pcIds);
    }

    /**
     * 删除项目申报类别信息
     *
     * @param pcId 项目申报类别主键
     * @return 结果
     */
    @Override
    public int deleteProjectCategoryByPcId(String pcId)
    {
        return projectCategoryMapper.deleteProjectCategoryByPcId(pcId);
    }
}
