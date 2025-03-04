package com.ruoyi.banner.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;

/**
 * 轮播图对象 banner
 *
 * @author zjh
 * @date 2024-12-26
 */
@Data
@TableName("banner")
public class Banner implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /** 轮播图名称 */
    @Excel(name = "轮播图名称")
    private String bannerName;

    /** 轮播图地址 */
    @Excel(name = "轮播图地址")
    private String url;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private Long isHidden;

    @TableField(exist = false)
    private String image;

}
