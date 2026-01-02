package com.fast.ich.service.impl;

import java.util.List;
import com.fast.system.general.utils.DateUtils;
import com.fast.system.general.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fast.ich.mapper.ContactMapper;
import com.fast.ich.domain.Contact;
import com.fast.ich.service.IContactService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.fast.system.general.utils.SecurityUtils.getUserId;

/**
 * 留言反馈Service业务层处理
 *
 * @author fast
 * @date 2025-11-07
 */
@Service
public class ContactServiceImpl implements IContactService
{
    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询留言反馈
     *
     * @param contactId 留言反馈主键
     * @return 留言反馈
     */
    @Override
    public Contact selectContactByContactId(String contactId)
    {
        return contactMapper.selectContactByContactId(contactId);
    }

    /**
     * 查询留言反馈列表
     *
     * @param contact 留言反馈
     * @return 留言反馈
     */
    @Override
    public List<Contact> selectContactList(Contact contact)
    {
        return contactMapper.selectContactList(contact);
    }

    /**
     * 新增留言反馈
     *
     * @param contact 留言反馈
     * @return 结果
     */
    @Override
    public int insertContact(Contact contact)
    {
        contact.setCreateTime(DateUtils.getNowDate());
        contact.setContactId(IdUtils.fastSimpleUUID());
        contact.setUserId(getUserId());
        return contactMapper.insertContact(contact);
    }

    /**
     * 批量新增留言反馈
     *
     * @param contacts 留言反馈List
     * @return 结果
     */
    @Override
    public int batchInsertContact(List<Contact> contacts)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(contacts)) {
            try {
                for (int i = 0; i < contacts.size(); i++) {
                    int row = contactMapper.insertContact(contacts.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == contacts.size() - 1;
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
     * 修改留言反馈
     *
     * @param contact 留言反馈
     * @return 结果
     */
    @Override
    public int updateContact(Contact contact)
    {
        return contactMapper.updateContact(contact);
    }

    /**
     * 批量删除留言反馈
     *
     * @param contactIds 需要删除的留言反馈主键
     * @return 结果
     */
    @Override
    public int deleteContactByContactIds(String[] contactIds)
    {
        return contactMapper.deleteContactByContactIds(contactIds);
    }

    /**
     * 删除留言反馈信息
     *
     * @param contactId 留言反馈主键
     * @return 结果
     */
    @Override
    public int deleteContactByContactId(String contactId)
    {
        return contactMapper.deleteContactByContactId(contactId);
    }
}
