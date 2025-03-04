package com.ruoyi.category.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * 分类对象 category
 *
 * @author zjh
 * @date 2024-11-15
 */
@Data
@TableName("category")
public class Category implements Serializable
{

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类背景图 */
    @Excel(name = "分类背景图")
    private String image;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private Long isHidden;

    private String icon;

    private String backgroundClass;

}
