package com.ruoyi.groupitem.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.groupitem.mapper.GroupItemMapper;
import com.ruoyi.groupitem.domain.GroupItem;
import com.ruoyi.groupitem.service.IGroupItemService;

/**
 * 分组信息Service业务层处理
 *
 * @author zjh
 * @date 2024-11-15
 */
@Service
public class GroupItemServiceImpl extends ServiceImpl<GroupItemMapper, GroupItem> implements IGroupItemService
{
    @Autowired
    private GroupItemMapper groupItemMapper;

    private QueryWrapper<GroupItem> getWrapper(GroupItem params){
        QueryWrapper<GroupItem> queryWrapper = new QueryWrapper<>();
        if (params != null){
            queryWrapper.like(StringUtils.isNotBlank(params.getGroupItemName()), "group_item_name", params.getGroupItemName());
            queryWrapper.eq(params.getIsHidden()!=null, "is_hidden", params.getIsHidden());
        }
        return queryWrapper;
    }

    /**
     * 查询分组信息
     *
     * @param id 分组信息主键
     * @return 分组信息
     */
    @Override
    public GroupItem selectGroupItemById(Long id)
    {
        return groupItemMapper.selectById(id);
    }

    /**
     * 查询分组信息列表
     *
     * @param groupItem 分组信息
     * @return 分组信息
     */
    @Override
    public List<GroupItem> selectGroupItemList(GroupItem groupItem)
    {
        return groupItemMapper.selectList(getWrapper(groupItem));
    }

    /**
     * 新增分组信息
     *
     * @param groupItem 分组信息
     * @return 结果
     */
    @Override
    public int insertGroupItem(GroupItem groupItem)
    {
        groupItem.setIsHidden(0L);
        return groupItemMapper.insert(groupItem);
    }

    /**
     * 修改分组信息
     *
     * @param groupItem 分组信息
     * @return 结果
     */
    @Override
    public int updateGroupItem(GroupItem groupItem)
    {
        return groupItemMapper.updateById(groupItem);
    }

    /**
     * 批量删除分组信息
     *
     * @param ids 需要删除的分组信息主键
     * @return 结果
     */
    @Override
    public int deleteGroupItemByIds(Long[] ids)
    {
        return groupItemMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除分组信息信息
     *
     * @param id 分组信息主键
     * @return 结果
     */
    @Override
    public int deleteGroupItemById(Long id)
    {
        return groupItemMapper.deleteById(id);
    }
}
