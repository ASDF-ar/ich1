package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Project;

/**
 * 项目申报Service接口
 *
 * @author fast
 * @date 2025-11-06
 */
public interface IProjectService
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
     * 批量新增项目申报
     *
     * @param projects 项目申报List
     * @return 结果
     */
    public int batchInsertProject(List<Project> projects);

    /**
     * 修改项目申报
     *
     * @param project 项目申报
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 批量删除项目申报
     *
     * @param projectIds 需要删除的项目申报主键集合
     * @return 结果
     */
    public int deleteProjectByProjectIds(String[] projectIds);

    /**
     * 删除项目申报信息
     *
     * @param projectId 项目申报主键
     * @return 结果
     */
    public int deleteProjectByProjectId(String projectId);
}
