package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.Master;
import com.fast.ich.domain.MasterContribution;

/**
 * 传承人Mapper接口
 *
 * @author fast
 * @date 2025-11-06
 */
@Mapper
public interface MasterMapper
{
    /**
     * 查询传承人
     *
     * @param masterId 传承人主键
     * @return 传承人
     */
    public Master selectMasterByMasterId(String masterId);

    /**
     * 查询传承人列表
     *
     * @param master 传承人
     * @return 传承人集合
     */
    public List<Master> selectMasterList(Master master);

    /**
     * 新增传承人
     *
     * @param master 传承人
     * @return 结果
     */
    public int insertMaster(Master master);

    /**
     * 修改传承人
     *
     * @param master 传承人
     * @return 结果
     */
    public int updateMaster(Master master);

    /**
     * 删除传承人
     *
     * @param masterId 传承人主键
     * @return 结果
     */
    public int deleteMasterByMasterId(String masterId);

    /**
     * 批量删除传承人
     *
     * @param masterIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMasterByMasterIds(String[] masterIds);

    /**
     * 批量删除传承贡献
     *
     * @param masterIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMasterContributionByMasterIds(String[] masterIds);

    /**
     * 批量新增传承贡献
     *
     * @param masterContributionList 传承贡献列表
     * @return 结果
     */
    public int batchMasterContribution(List<MasterContribution> masterContributionList);


    /**
     * 通过传承人主键删除传承贡献信息
     *
     * @param masterId 传承人ID
     * @return 结果
     */
    public int deleteMasterContributionByMasterId(String masterId);
}
