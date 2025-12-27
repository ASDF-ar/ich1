package com.fast.ich.domain;

import com.fast.system.general.annotation.Excel;
import com.fast.system.general.core.domain.BaseEntity;
import lombok.*;

/**
 * 新闻资讯对象 news
 *
 * @author fast
 * @date 2025-12-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 新闻资讯ID */
    private String newsId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 摘要 */
    @Excel(name = "摘要")
    private String summary;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 封面 */
    @Excel(name = "封面")
    private String image;

    /** 发布单位 */
    @Excel(name = "发布单位")
    private String author;

    /** 类型 */
    @Excel(name = "类型")
    private String category;


}
