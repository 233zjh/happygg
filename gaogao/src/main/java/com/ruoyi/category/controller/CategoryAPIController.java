package com.ruoyi.category.controller;

import com.ruoyi.category.domain.Category;
import com.ruoyi.category.service.ICategoryService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类APIController
 *
 * @author zjh
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/api/category")
public class CategoryAPIController extends BaseController
{
    @Autowired
    private ICategoryService categoryService;

    /**
     * 查询分类列表（不分页）
     */
    @GetMapping("/listAll")
    public AjaxResult listAll(Category category)
    {
        category.setIsHidden(0L);
        List<Category> list = categoryService.selectCategoryList(category);
        return AjaxResult.success(list);
    }

    /**
     * 获取分类详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(categoryService.selectCategoryById(id));
    }

}
