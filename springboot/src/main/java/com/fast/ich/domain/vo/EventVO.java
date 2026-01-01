package com.fast.ich.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 活动预告VO(用于前台新闻资讯-活动预告)
 */
@Data
public class EventVO {
    /** 活动预告ID */
    private String eventId;

    /** 活动名称 */
    private String title;

    /** 活动日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /** 日期 */
    private String day;

    /** 月份 */
    private String month;

    /** 活动地点 */
    private String location;
}