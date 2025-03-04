package com.ruoyi.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.item.domain.Item;

import java.util.List;

/**
 * 图片或视频Mapper接口
 *
 * @author zjh
 * @date 2024-11-15
 */
public interface ItemMapper extends BaseMapper<Item>
{
    /**
     * 查询图片或视频列表
     *
     * @param item 图片或视频
     * @return 图片或视频集合
     */
    public List<Item> selectItemList(Item item);
}
