package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;
/**
 * 传承贡献对象 master_contribution
 *
 * @author fast
 * @date 2025-11-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterContribution extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 传承贡献ID */
    private Long contributionId;

    /** 传承人ID */
    @Excel(name = "传承人ID")
    private String masterId;

    /** 贡献内容 */
    @Excel(name = "贡献内容")
    private String content;


}
