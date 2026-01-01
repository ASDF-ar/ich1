package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Heritage;

/**
 * 非遗项目Service接口
 *
 * @author fast
 * @date 2025-12-24
 */
public interface IHeritageService
{
    /**
     * 查询非遗项目
     *
     * @param heritageId 非遗项目主键
     * @return 非遗项目
     */
    public Heritage selectHeritageByHeritageId(String heritageId);

    /**
     * 查询非遗项目列表
     *
     * @param heritage 非遗项目
     * @return 非遗项目集合
     */
    public List<Heritage> selectHeritageList(Heritage heritage);

    /**
     * 新增非遗项目
     *
     * @param heritage 非遗项目
     * @return 结果
     */
    public int insertHeritage(Heritage heritage);

    /**
     * 批量新增非遗项目
     *
     * @param heritages 非遗项目List
     * @return 结果
     */
    public int batchInsertHeritage(List<Heritage> heritages);

    /**
     * 修改非遗项目
     *
     * @param heritage 非遗项目
     * @return 结果
     */
    public int updateHeritage(Heritage heritage);

    /**
     * 批量删除非遗项目
     *
     * @param heritageIds 需要删除的非遗项目主键集合
     * @return 结果
     */
    public int deleteHeritageByHeritageIds(String[] heritageIds);

    /**
     * 删除非遗项目信息
     *
     * @param heritageId 非遗项目主键
     * @return 结果
     */
    public int deleteHeritageByHeritageId(String heritageId);
    /**
     * 提取唯一分类字段并返回给前端
     * @return
     */
    List<String> selectOnlyCategories();
}
