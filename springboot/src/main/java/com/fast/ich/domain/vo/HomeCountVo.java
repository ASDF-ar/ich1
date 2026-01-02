package com.fast.ich.domain.vo;

import lombok.Data;

/**
 * 首页顶部数据统计VO
 */
@Data
public class HomeCountVo {
    private Integer heritageCount; //非遗项目数
    private Integer masterCount; //传承人数
    private Integer newsCount; //新闻资讯数
    private Integer userCount; //用户数
}