package com.ruoyi.category.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.category.domain.Category;

/**
 * 分类Service接口
 *
 * @author zjh
 * @date 2024-11-15
 */
public interface ICategoryService extends IService<Category>
{
    /**
     * 查询分类
     *
     * @param id 分类主键
     * @return 分类
     */
    public Category selectCategoryById(Long id);

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类主键集合
     * @return 结果
     */
    public int deleteCategoryByIds(Long[] ids);

    /**
     * 删除分类信息
     *
     * @param id 分类主键
     * @return 结果
     */
    public int deleteCategoryById(Long id);

}
