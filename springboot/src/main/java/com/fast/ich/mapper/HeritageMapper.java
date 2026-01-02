package com.fast.ich.mapper;

import java.util.List;

import com.fast.ich.domain.vo.PieVO;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.Heritage;

/**
 * 非遗项目Mapper接口
 *
 * @author fast
 * @date 2025-12-24
 */
@Mapper
public interface HeritageMapper
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
     * 修改非遗项目
     *
     * @param heritage 非遗项目
     * @return 结果
     */
    public int updateHeritage(Heritage heritage);

    /**
     * 删除非遗项目
     *
     * @param heritageId 非遗项目主键
     * @return 结果
     */
    public int deleteHeritageByHeritageId(String heritageId);

    /**
     * 批量删除非遗项目
     *
     * @param heritageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHeritageByHeritageIds(String[] heritageIds);

    /**
     * @return
     */
    /**
     * 提取唯一分类字段并返回给前端
     * @return
     */
    List<String> selectOnlyCategories();

//     selectCategoryChart();

    /**
     * 查询每种项目分类各有多少个
     * @return
     */
    List<PieVO> selectCategoryChart();
}
