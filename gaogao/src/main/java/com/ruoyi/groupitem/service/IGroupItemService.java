package com.ruoyi.groupitem.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.groupitem.domain.GroupItem;

/**
 * 分组信息Service接口
 *
 * @author zjh
 * @date 2024-11-15
 */
public interface IGroupItemService extends IService<GroupItem>
{
    /**
     * 查询分组信息
     *
     * @param id 分组信息主键
     * @return 分组信息
     */
    public GroupItem selectGroupItemById(Long id);

    /**
     * 查询分组信息列表
     *
     * @param groupItem 分组信息
     * @return 分组信息集合
     */
    public List<GroupItem> selectGroupItemList(GroupItem groupItem);

    /**
     * 新增分组信息
     *
     * @param groupItem 分组信息
     * @return 结果
     */
    public int insertGroupItem(GroupItem groupItem);

    /**
     * 修改分组信息
     *
     * @param groupItem 分组信息
     * @return 结果
     */
    public int updateGroupItem(GroupItem groupItem);

    /**
     * 批量删除分组信息
     *
     * @param ids 需要删除的分组信息主键集合
     * @return 结果
     */
    public int deleteGroupItemByIds(Long[] ids);

    /**
     * 删除分组信息信息
     *
     * @param id 分组信息主键
     * @return 结果
     */
    public int deleteGroupItemById(Long id);
}
