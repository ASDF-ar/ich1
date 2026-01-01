package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 项目申报类别对象 project_category
 *
 * @author fast
 * @date 2025-11-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类别ID */
    private String pcId;

    /** 类别名称 */
    @Excel(name = "类别名称")
    private String name;


}
