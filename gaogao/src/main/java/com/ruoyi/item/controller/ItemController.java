package com.ruoyi.item.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.groupitem.domain.GroupItem;
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
import com.ruoyi.item.domain.Item;
import com.ruoyi.item.service.IItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图片或视频Controller
 *
 * @author zjh
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/gaogao/item")
public class ItemController extends BaseController
{
    @Autowired
    private IItemService itemService;

    /**
     * 查询图片或视频列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(Item item)
    {
        startPage();
        List<Item> list = itemService.selectItemList(item);
        return getDataTable(list);
    }

    /**
     * 导出图片或视频列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:export')")
    @Log(title = "图片或视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Item item)
    {
        List<Item> list = itemService.selectItemList(item);
        ExcelUtil<Item> util = new ExcelUtil<Item>(Item.class);
        util.exportExcel(response, list, "图片或视频数据");
    }

    /**
     * 获取图片或视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(itemService.selectItemById(id));
    }

    /**
     * 新增图片或视频
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:add')")
    @Log(title = "图片或视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Item item)
    {
        return toAjax(itemService.insertItem(item));
    }

    /**
     * 修改图片或视频
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:edit')")
    @Log(title = "图片或视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Item item)
    {
        return toAjax(itemService.updateItem(item));
    }

    /**
     * 删除图片或视频
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:remove')")
    @Log(title = "图片或视频", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(itemService.deleteItemByIds(ids));
    }

    /**
     * 分组显隐
     */
    @PreAuthorize("@ss.hasPermi('gaogao:item:edit')")
    @Log(title = "分组信息", businessType = BusinessType.UPDATE)
    @PostMapping("/hidden")
    public AjaxResult hidden(@RequestBody Item item)
    {
        return toAjax(itemService.updateItem(item));
    }
}
