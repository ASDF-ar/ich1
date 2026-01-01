package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 非遗项目对象 heritage
 *
 * @author fast
 * @date 2025-12-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Heritage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 非遗项目ID */
    private String heritageId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String title;

    /** 项目简介 */
    @Excel(name = "项目简介")
    private String description;

    /** 详细介绍 */
    @Excel(name = "详细介绍")
    private String detailDescription;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 所在地 */
    @Excel(name = "所在地")
    private String location;

    /** 类别 */
    @Excel(name = "类别")
    private String category;

    /** 传承状态 */
    @Excel(name = "传承状态")
    private String status;


}
