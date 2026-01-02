package com.fast.ich.service.impl;

import java.util.List;

import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.fast.system.general.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.fast.ich.domain.MasterContribution;
import com.fast.ich.mapper.MasterMapper;
import com.fast.ich.domain.Master;
import com.fast.ich.service.IMasterService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 传承人Service业务层处理
 *
 * @author fast
 * @date 2025-11-06
 */
@Service
public class MasterServiceImpl implements IMasterService
{
    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询传承人
     *
     * @param masterId 传承人主键
     * @return 传承人
     */
    @Override
    public Master selectMasterByMasterId(String masterId)
    {
        return masterMapper.selectMasterByMasterId(masterId);
    }

    /**
     * 查询传承人列表
     *
     * @param master 传承人
     * @return 传承人
     */
    @Override
    public List<Master> selectMasterList(Master master)
    {
        return masterMapper.selectMasterList(master);
    }

    /**
     * 新增传承人
     *
     * @param master 传承人
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMaster(Master master)
    {
        master.setMasterId(IdUtils.fastSimpleUUID());
        int rows = masterMapper.insertMaster(master);
        insertMasterContribution(master);
        return rows;
    }

    /**
     * 批量新增传承人
     *
     * @param masters 传承人List
     * @return 结果
     */
    @Override
    public int batchInsertMaster(List<Master> masters)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(masters)) {
            try {
                for (int i = 0; i < masters.size(); i++) {
                    int row = masterMapper.insertMaster(masters.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == masters.size() - 1;
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
     * 修改传承人
     *
     * @param master 传承人
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMaster(Master master)
    {
        masterMapper.deleteMasterContributionByMasterId(master.getMasterId());
        insertMasterContribution(master);
        return masterMapper.updateMaster(master);
    }

    /**
     * 批量删除传承人
     *
     * @param masterIds 需要删除的传承人主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMasterByMasterIds(String[] masterIds)
    {
        masterMapper.deleteMasterContributionByMasterIds(masterIds);
        return masterMapper.deleteMasterByMasterIds(masterIds);
    }

    /**
     * 删除传承人信息
     *
     * @param masterId 传承人主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMasterByMasterId(String masterId)
    {
        masterMapper.deleteMasterContributionByMasterId(masterId);
        return masterMapper.deleteMasterByMasterId(masterId);
    }

    /**
     * 新增传承贡献信息
     *
     * @param master 传承人对象
     */
    public void insertMasterContribution(Master master)
    {
        List<MasterContribution> masterContributionList = master.getMasterContributionList();
        String masterId = master.getMasterId();
        if (StringUtils.isNotNull(masterContributionList))
        {
            List<MasterContribution> list = new ArrayList<MasterContribution>();
            for (MasterContribution masterContribution : masterContributionList)
            {
                masterContribution.setMasterId(masterId);
                list.add(masterContribution);
            }
            if (list.size() > 0)
            {
                masterMapper.batchMasterContribution(list);
            }
        }
    }
}
