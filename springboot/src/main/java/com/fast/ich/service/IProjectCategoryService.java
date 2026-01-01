package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.ProjectCategory;

/**
 * 项目申报类别Service接口
 *
 * @author fast
 * @date 2025-11-05
 */
public interface IProjectCategoryService
{
    /**
     * 查询项目申报类别
     *
     * @param pcId 项目申报类别主键
     * @return 项目申报类别
     */
    public ProjectCategory selectProjectCategoryByPcId(String pcId);

    /**
     * 查询项目申报类别列表
     *
     * @param projectCategory 项目申报类别
     * @return 项目申报类别集合
     */
    public List<ProjectCategory> selectProjectCategoryList(ProjectCategory projectCategory);

    /**
     * 新增项目申报类别
     *
     * @param projectCategory 项目申报类别
     * @return 结果
     */
    public int insertProjectCategory(ProjectCategory projectCategory);

    /**
     * 批量新增项目申报类别
     *
     * @param projectCategorys 项目申报类别List
     * @return 结果
     */
    public int batchInsertProjectCategory(List<ProjectCategory> projectCategorys);

    /**
     * 修改项目申报类别
     *
     * @param projectCategory 项目申报类别
     * @return 结果
     */
    public int updateProjectCategory(ProjectCategory projectCategory);

    /**
     * 批量删除项目申报类别
     *
     * @param pcIds 需要删除的项目申报类别主键集合
     * @return 结果
     */
    public int deleteProjectCategoryByPcIds(String[] pcIds);

    /**
     * 删除项目申报类别信息
     *
     * @param pcId 项目申报类别主键
     * @return 结果
     */
    public int deleteProjectCategoryByPcId(String pcId);
}
