package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 关于我们对象 about
 *
 * @author fast
 * @date 2025-11-07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class About extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 关于我们ID */
    private String aboutId;

    /** 介绍 */
    @Excel(name = "介绍")
    private String intro;

    /** 宣传图片 */
    @Excel(name = "宣传图片")
    private String image;

    /** 我们的使命 */
    @Excel(name = "我们的使命")
    private String mission;

    /** 我们的愿景 */
    @Excel(name = "我们的愿景")
    private String vision;

    /** 我们的价值观 */
    @Excel(name = "我们的价值观")
    private String value;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 办公地址 */
    @Excel(name = "办公地址")
    private String address;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;


}
