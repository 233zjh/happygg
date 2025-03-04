package com.ruoyi.groupitem.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.groupitem.domain.GroupItem;
import com.ruoyi.groupitem.service.IGroupItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分组信息APIController
 *
 * @author zjh
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/api/groupitem")
public class GroupItemAPIController extends BaseController
{
    @Autowired
    private IGroupItemService groupItemService;

    /**
     * 查询分组信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GroupItem groupItem)
    {
        startPage();
        List<GroupItem> list = groupItemService.selectGroupItemList(groupItem);
        return getDataTable(list);
    }

    /**
     * 查询分类列表（不分页）
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(GroupItem groupItem)
    {
        List<GroupItem> list = groupItemService.selectGroupItemList(groupItem);
        return AjaxResult.success(list);
    }

    /**
     * 获取分组信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(groupItemService.selectGroupItemById(id));
    }

}
