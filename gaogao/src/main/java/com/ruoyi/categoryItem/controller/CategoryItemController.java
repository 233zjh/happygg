package com.ruoyi.categoryItem.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.categoryItem.domain.CategoryItem;
import com.ruoyi.categoryItem.service.ICategoryItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图片视频-分类中间表Controller
 * 
 * @author zjh
 * @date 2024-12-30
 */
@RestController
@RequestMapping("/gaogao/categoryItem")
public class CategoryItemController extends BaseController
{
    @Autowired
    private ICategoryItemService categoryItemService;

    /**
     * 查询图片视频-分类中间表列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryItem categoryItem)
    {
        startPage();
        List<CategoryItem> list = categoryItemService.selectCategoryItemList(categoryItem);
        return getDataTable(list);
    }

    /**
     * 导出图片视频-分类中间表列表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:export')")
    @Log(title = "图片视频-分类中间表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CategoryItem categoryItem)
    {
        List<CategoryItem> list = categoryItemService.selectCategoryItemList(categoryItem);
        ExcelUtil<CategoryItem> util = new ExcelUtil<CategoryItem>(CategoryItem.class);
        util.exportExcel(response, list, "图片视频-分类中间表数据");
    }

    /**
     * 获取图片视频-分类中间表详细信息
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(categoryItemService.selectCategoryItemById(id));
    }

    /**
     * 新增图片视频-分类中间表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:add')")
    @Log(title = "图片视频-分类中间表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryItem categoryItem)
    {
        return toAjax(categoryItemService.insertCategoryItem(categoryItem));
    }

    /**
     * 修改图片视频-分类中间表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:edit')")
    @Log(title = "图片视频-分类中间表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryItem categoryItem)
    {
        return toAjax(categoryItemService.updateCategoryItem(categoryItem));
    }

    /**
     * 删除图片视频-分类中间表
     */
    @PreAuthorize("@ss.hasPermi('gaogao:categoryItem:remove')")
    @Log(title = "图片视频-分类中间表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(categoryItemService.deleteCategoryItemByIds(ids));
    }
}
