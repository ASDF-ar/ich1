package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * 留言反馈对象 contact
 *
 * @author fast
 * @date 2025-11-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 留言反馈ID */
    private String contactId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 留言内容 */
    @Excel(name = "留言内容")
    private String message;

    /** 留言人用户ID */
    @Excel(name = "留言人用户ID")
    private Long userId;

    //留言人用户名
    private String userName;


//    public void setCreateTime(Date nowDate) {
//    }
}
