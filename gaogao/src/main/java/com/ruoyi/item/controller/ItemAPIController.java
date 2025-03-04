package com.ruoyi.item.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.item.domain.Item;
import com.ruoyi.item.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图片或视频APIController
 *
 * @author zjh
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/api/item")
public class ItemAPIController extends BaseController
{
    @Autowired
    private IItemService itemService;

    /**
     * 查询图片或视频列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Item item)
    {
        startPage();
        List<Item> list = itemService.selectItemList(item);
        return getDataTable(list);
    }

    /**
     * 查询图片或视频列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(Item item)
    {
        item.setIsHidden(0L);
        List<Item> list = itemService.selectItemList(item);
        return AjaxResult.success(list);
    }

    /**
     * 获取图片或视频详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(itemService.selectItemById(id));
    }

    /**
     * 新增图片或视频
     */
    @Log(title = "图片或视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Item item)
    {
        return toAjax(itemService.insertItem(item));
    }

    /**
     * 修改图片或视频
     */
    @Log(title = "图片或视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Item item)
    {
        return toAjax(itemService.updateItem(item));
    }

    /**
     * 删除图片或视频
     */
    @Log(title = "图片或视频", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(itemService.deleteItemByIds(ids));
    }
}
