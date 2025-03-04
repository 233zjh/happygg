package com.ruoyi.item.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.item.mapper.ItemMapper;
import com.ruoyi.item.domain.Item;
import com.ruoyi.item.service.IItemService;

/**
 * 图片或视频Service业务层处理
 *
 * @author zjh
 * @date 2024-11-15
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService
{
    @Autowired
    private ItemMapper itemMapper;

    private QueryWrapper<Item> getWrapper(Item params){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        if (params != null){
            queryWrapper.like(StringUtils.isNotBlank(params.getName()), "name", params.getName());
            queryWrapper.eq(params.getType()!=null, "type", params.getType());
            queryWrapper.eq(params.getGroupItemId()!=null, "group_item_id", params.getGroupItemId());
            queryWrapper.eq(params.getIsHidden()!=null, "is_hidden", params.getIsHidden());
        }
        return queryWrapper;
    }

    /**
     * 查询图片或视频
     *
     * @param id 图片或视频主键
     * @return 图片或视频
     */
    @Override
    public Item selectItemById(Long id)
    {
        return itemMapper.selectById(id);
    }

    /**
     * 查询图片或视频列表
     *
     * @param item 图片或视频
     * @return 图片或视频
     */
    @Override
    public List<Item> selectItemList(Item item)
    {
        return itemMapper.selectItemList(item);
    }

    /**
     * 新增图片或视频
     *
     * @param item 图片或视频
     * @return 结果
     */
    @Override
    public int insertItem(Item item)
    {
        if(item.getFilePath().contains(",")){
            String [] filePaths = item.getFilePath().split(",");
            for (String filePath : filePaths) {
                item.setId(null);
                item.setFilePath(filePath);
                item.setIsHidden(0L);
                itemMapper.insert(item);
            }
            return filePaths.length;
        }else{
            return itemMapper.insert(item);
        }
    }

    /**
     * 修改图片或视频
     *
     * @param item 图片或视频
     * @return 结果
     */
    @Override
    public int updateItem(Item item)
    {
        return itemMapper.updateById(item);
    }

    /**
     * 批量删除图片或视频
     *
     * @param ids 需要删除的图片或视频主键
     * @return 结果
     */
    @Override
    public int deleteItemByIds(Long[] ids)
    {
        return itemMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除图片或视频信息
     *
     * @param id 图片或视频主键
     * @return 结果
     */
    @Override
    public int deleteItemById(Long id)
    {
        return itemMapper.deleteById(id);
    }
}
