package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.ProjectCategory;

/**
 * 项目申报类别Mapper接口
 *
 * @author fast
 * @date 2025-11-05
 */
@Mapper
public interface ProjectCategoryMapper
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
     * 修改项目申报类别
     *
     * @param projectCategory 项目申报类别
     * @return 结果
     */
    public int updateProjectCategory(ProjectCategory projectCategory);

    /**
     * 删除项目申报类别
     *
     * @param pcId 项目申报类别主键
     * @return 结果
     */
    public int deleteProjectCategoryByPcId(String pcId);

    /**
     * 批量删除项目申报类别
     *
     * @param pcIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectCategoryByPcIds(String[] pcIds);
}
