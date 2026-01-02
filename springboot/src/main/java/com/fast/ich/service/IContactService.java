package com.fast.ich.service;

import java.util.List;
import com.fast.ich.domain.Contact;

/**
 * 留言反馈Service接口
 *
 * @author fast
 * @date 2025-11-07
 */
public interface IContactService
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
     * 批量新增留言反馈
     *
     * @param contacts 留言反馈List
     * @return 结果
     */
    public int batchInsertContact(List<Contact> contacts);

    /**
     * 修改留言反馈
     *
     * @param contact 留言反馈
     * @return 结果
     */
    public int updateContact(Contact contact);

    /**
     * 批量删除留言反馈
     *
     * @param contactIds 需要删除的留言反馈主键集合
     * @return 结果
     */
    public int deleteContactByContactIds(String[] contactIds);

    /**
     * 删除留言反馈信息
     *
     * @param contactId 留言反馈主键
     * @return 结果
     */
    public int deleteContactByContactId(String contactId);
}
