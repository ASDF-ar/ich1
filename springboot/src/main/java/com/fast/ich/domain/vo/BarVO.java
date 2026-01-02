package com.fast.ich.domain.vo;

import lombok.Data;

/**
 * 数据统计柱状图VO
 */
@Data
public class BarVO {
    //类别数组
    private String[] categories;
    //数据数组
    private Integer[] data;
}