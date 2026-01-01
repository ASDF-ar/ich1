package com.fast.ich.domain;

import java.util.Date;

import com.fast.system.general.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fast.system.general.annotation.Excel;
import lombok.*;

/**
 * 活动预告对象 event
 *
 * @author fast
 * @date 2025-11-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动预告ID */
    private String eventId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String title;

    /** 活动日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 活动时间 */
    @Excel(name = "活动时间")
    private String time;

    /** 活动地点 */
    @Excel(name = "活动地点")
    private String location;

    /** 主办单位 */
    @Excel(name = "主办单位")
    private String organizer;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 活动详情 */
    @Excel(name = "活动详情")
    private String content;


}
