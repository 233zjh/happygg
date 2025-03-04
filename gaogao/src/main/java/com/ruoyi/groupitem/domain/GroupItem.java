package com.ruoyi.groupitem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * 分组信息对象 group_item
 *
 * @author zjh
 * @date 2024-11-15
 */
@Data
@TableName("group_item")
public class GroupItem implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分组 */
    @Excel(name = "分组")
    private String groupItemName;

    /** 背景图片 */
    @Excel(name = "背景图片")
    private String image;

    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private Long isHidden;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 数量
     */
    @TableField(exist = false)
    private Integer itemCount;

}
