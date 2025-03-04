package com.ruoyi.item.service;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.item.domain.Item;

/**
 * 图片或视频Service接口
 *
 * @author zjh
 * @date 2024-11-15
 */
public interface IItemService extends IService<Item>
{
    /**
     * 查询图片或视频
     *
     * @param id 图片或视频主键
     * @return 图片或视频
     */
    public Item selectItemById(Long id);

    /**
     * 查询图片或视频列表
     *
     * @param item 图片或视频
     * @return 图片或视频集合
     */
    public List<Item> selectItemList(Item item);

    /**
     * 新增图片或视频
     *
     * @param item 图片或视频
     * @return 结果
     */
    public int insertItem(Item item);

    /**
     * 修改图片或视频
     *
     * @param item 图片或视频
     * @return 结果
     */
    public int updateItem(Item item);

    /**
     * 批量删除图片或视频
     *
     * @param ids 需要删除的图片或视频主键集合
     * @return 结果
     */
    public int deleteItemByIds(Long[] ids);

    /**
     * 删除图片或视频信息
     *
     * @param id 图片或视频主键
     * @return 结果
     */
    public int deleteItemById(Long id);
}
