package com.fast.ich.domain;

import java.util.List;
import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 传承人对象 master
 *
 * @author fast
 * @date 2025-11-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Master extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 传承人ID */
    private String masterId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 技艺专长 */
    @Excel(name = "技艺专长")
    private String skills;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 照片 */
    @Excel(name = "照片")
    private String image;

    /** 传承贡献信息 */
    private List<MasterContribution> masterContributionList;


}
