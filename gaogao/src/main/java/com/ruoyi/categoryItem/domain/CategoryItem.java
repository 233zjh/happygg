package com.ruoyi.categoryItem.domain;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
/**
 * 图片视频-分类中间表对象 category_item
 *
 * @author zjh
 * @date 2024-12-30
 */
@Data
@TableName("category_item")
public class CategoryItem implements Serializable {
    private static final long serialVersionUID = 1L;


    /** 主键 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /** 分类id */
    @Excel(name = "分类id")
    private Long categoryId;


    /** 图片或视频id */
    @Excel(name = "图片或视频id")
    private Long itemId;


}
