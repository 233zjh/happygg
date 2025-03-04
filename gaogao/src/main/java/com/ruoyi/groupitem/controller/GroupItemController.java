package com.ruoyi.groupitem.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.category.domain.Category;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.groupitem.domain.GroupItem;
import com.ruoyi.groupitem.service.IGroupItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分组信息Controller
 *
 * @author zjh
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/gaogao/groupitem")
public class GroupItemController extends BaseController
{
    @Autowired
    private IGroupItemService groupItemService;

    /**
     * 查询分组信息列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(GroupItem groupItem)
    {
        startPage();
        List<GroupItem> list = groupItemService.selectGroupItemList(groupItem);
        return getDataTable(list);
    }

    /**
     * 查询分类列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(GroupItem groupItem)
    {
        List<GroupItem> list = groupItemService.selectGroupItemList(groupItem);
        return AjaxResult.success(list);
    }

    /**
     * 导出分组信息列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:export')")
    @Log(title = "分组信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GroupItem groupItem)
    {
        List<GroupItem> list = groupItemService.selectGroupItemList(groupItem);
        ExcelUtil<GroupItem> util = new ExcelUtil<GroupItem>(GroupItem.class);
        util.exportExcel(response, list, "分组信息数据");
    }

    /**
     * 获取分组信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(groupItemService.selectGroupItemById(id));
    }

    /**
     * 新增分组信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:add')")
    @Log(title = "分组信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GroupItem groupItem)
    {
        return toAjax(groupItemService.insertGroupItem(groupItem));
    }

    /**
     * 修改分组信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:edit')")
    @Log(title = "分组信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GroupItem groupItem)
    {
        return toAjax(groupItemService.updateGroupItem(groupItem));
    }

    /**
     * 删除分组信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:remove')")
    @Log(title = "分组信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(groupItemService.deleteGroupItemByIds(ids));
    }

    /**
     * 分组显隐
     */
    @PreAuthorize("@ss.hasPermi('gaogao:groupitem:edit')")
    @Log(title = "分组信息", businessType = BusinessType.UPDATE)
    @PostMapping("/hidden")
    public AjaxResult hidden(@RequestBody GroupItem groupItem)
    {
        return toAjax(groupItemService.updateGroupItem(groupItem));
    }
}
