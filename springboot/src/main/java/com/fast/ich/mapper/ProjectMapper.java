package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.Project;

/**
 * 项目申报Mapper接口
 *
 * @author fast
 * @date 2025-11-06
 */
@Mapper
public interface ProjectMapper
{
    /**
     * 查询项目申报
     *
     * @param projectId 项目申报主键
     * @return 项目申报
     */
    public Project selectProjectByProjectId(String projectId);

    /**
     * 查询项目申报列表
     *
     * @param project 项目申报
     * @return 项目申报集合
     */
    public List<Project> selectProjectList(Project project);

    /**
     * 新增项目申报
     *
     * @param project 项目申报
     * @return 结果
     */
    public int insertProject(Project project);

    /**
     * 修改项目申报
     *
     * @param project 项目申报
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 删除项目申报
     *
     * @param projectId 项目申报主键
     * @return 结果
     */
    public int deleteProjectByProjectId(String projectId);

    /**
     * 批量删除项目申报
     *
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectByProjectIds(String[] projectIds);
}
