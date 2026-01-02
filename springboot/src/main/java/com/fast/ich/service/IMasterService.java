package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Master;

/**
 * 传承人Service接口
 *
 * @author fast
 * @date 2025-11-06
 */
public interface IMasterService
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
     * 批量新增传承人
     *
     * @param masters 传承人List
     * @return 结果
     */
    public int batchInsertMaster(List<Master> masters);

    /**
     * 修改传承人
     *
     * @param master 传承人
     * @return 结果
     */
    public int updateMaster(Master master);

    /**
     * 批量删除传承人
     *
     * @param masterIds 需要删除的传承人主键集合
     * @return 结果
     */
    public int deleteMasterByMasterIds(String[] masterIds);

    /**
     * 删除传承人信息
     *
     * @param masterId 传承人主键
     * @return 结果
     */
    public int deleteMasterByMasterId(String masterId);
}
