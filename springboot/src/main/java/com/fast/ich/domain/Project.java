package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 项目申报对象 project
 *
 * @author fast
 * @date 2025-11-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目申报ID */
    private String projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 项目申报类别ID */
    @Excel(name = "项目申报类别ID")
    private String categoryId;

    /** 项目简介 */
    @Excel(name = "项目简介")
    private String introduction;

    /** 详细描述 */
    @Excel(name = "详细描述")
    private String description;

    /** 传承谱系 */
    @Excel(name = "传承谱系")
    private String inheritance;

    /** 传承人信息 */
    @Excel(name = "传承人信息")
    private String inheritors;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 申报人用户ID */
    @Excel(name = "申报人用户ID")
    private Long userId;

    // 申报人用户名
    private String userName;

    //项目申报类别名称
    private String categoryName;

}
