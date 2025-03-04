package com.ruoyi.item.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * 图片或视频对象 item
 *
 * @author zjh
 * @date 2024-11-15
 */
@Data
@TableName("item")
public class Item implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    private Long type;

    /** 分组id */
    @Excel(name = "分组id")
    private Long groupItemId;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 是否隐藏1是0否 */
    @Excel(name = "是否隐藏1是0否")
    private Long isHidden;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    @TableField(exist = false)
    private String groupItemName;

}
