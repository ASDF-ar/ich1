package com.fast.ich.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fast.ich.domain.Contact;

/**
 * 留言反馈Mapper接口
 *
 * @author fast
 * @date 2025-11-07
 */
@Mapper
public interface ContactMapper
{
    /**
     * 查询留言反馈
     *
     * @param contactId 留言反馈主键
     * @return 留言反馈
     */
    public Contact selectContactByContactId(String contactId);

    /**
     * 查询留言反馈列表
     *
     * @param contact 留言反馈
     * @return 留言反馈集合
     */
    public List<Contact> selectContactList(Contact contact);

    /**
     * 新增留言反馈
     *
     * @param contact 留言反馈
     * @return 结果
     */
    public int insertContact(Contact contact);

    /**
     * 修改留言反馈
     *
     * @param contact 留言反馈
     * @return 结果
     */
    public int updateContact(Contact contact);

    /**
     * 删除留言反馈
     *
     * @param contactId 留言反馈主键
     * @return 结果
     */
    public int deleteContactByContactId(String contactId);

    /**
     * 批量删除留言反馈
     *
     * @param contactIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContactByContactIds(String[] contactIds);
}
