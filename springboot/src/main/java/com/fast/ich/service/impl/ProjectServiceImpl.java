package com.fast.ich.service.impl;

import java.util.List;
import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.SecurityUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.ProjectMapper;
import com.fast.ich.domain.Project;
import com.fast.ich.service.IProjectService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 项目申报Service业务层处理
 *
 * @author fast
 * @date 2025-11-06
 */
@Service
public class ProjectServiceImpl implements IProjectService
{
    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询项目申报
     *
     * @param projectId 项目申报主键
     * @return 项目申报
     */
    @Override
    public Project selectProjectByProjectId(String projectId)
    {
        return projectMapper.selectProjectByProjectId(projectId);
    }

    /**
     * 查询项目申报列表
     *
     * @param project 项目申报
     * @return 项目申报
     */
    @Override
    public List<Project> selectProjectList(Project project)
    {
        return projectMapper.selectProjectList(project);
    }

    /**
     * 新增项目申报
     *
     * @param project 项目申报
     * @return 结果
     */
    @Override
    public int insertProject(Project project) {
        project.setCreateTime(DateUtils.getNowDate());
        //生成一个UUID并插入至项目申报对象中
        project.setProjectId(IdUtils.fastSimpleUUID());
        //获取当前操作用户ID并插入至项目申报对象中
        project.setUserId(getUserId());
        return projectMapper.insertProject(project);
    }

    /**
     * 批量新增项目申报
     *
     * @param projects 项目申报List
     * @return 结果
     */
    @Override
    public int batchInsertProject(List<Project> projects)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(projects)) {
            try {
                for (int i = 0; i < projects.size(); i++) {
                    int row = projectMapper.insertProject(projects.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == projects.size() - 1;
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
     * 修改项目申报
     *
     * @param project 项目申报
     * @return 结果
     */
    @Override
    public int updateProject(Project project)
    {
        return projectMapper.updateProject(project);
    }

    /**
     * 批量删除项目申报
     *
     * @param projectIds 需要删除的项目申报主键
     * @return 结果
     */
    @Override
    public int deleteProjectByProjectIds(String[] projectIds)
    {
        return projectMapper.deleteProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目申报信息
     *
     * @param projectId 项目申报主键
     * @return 结果
     */
    @Override
    public int deleteProjectByProjectId(String projectId)
    {
        return projectMapper.deleteProjectByProjectId(projectId);
    }
}
