package com.ruoyi.categoryItem.service;

import java.util.List;
import com.ruoyi.categoryItem.domain.CategoryItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 图片视频-分类中间表Service接口
 *
 * @author zjh
 * @date 2024-12-30
 */
public interface ICategoryItemService extends IService<CategoryItem> {

    /**
     * 查询图片视频-分类中间表列表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 图片视频-分类中间表集合
     */
    List<CategoryItem> selectCategoryItemList(CategoryItem categoryItem);

    /**
     * 新增图片视频-分类中间表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 结果
     */
    int insertCategoryItem(CategoryItem categoryItem);

    /**
     * 修改图片视频-分类中间表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 结果
     */
    int updateCategoryItem(CategoryItem categoryItem);

    /**
     * 批量删除图片视频-分类中间表
     *
     * @param ids 需要删除的图片视频-分类中间表主键集合
     * @return 结果
     */
    int deleteCategoryItemByIds(Long[] ids);

    /**
     * 删除图片视频-分类中间表信息
     *
     * @param id 图片视频-分类中间表主键
     * @return 结果
     */
    int deleteCategoryItemById(Long id);

    /**
     * 获取图片视频-分类中间表信息
     *
     * @param id 图片视频-分类中间表主键
     * @return 结果
     */
     CategoryItem selectCategoryItemById(Long id);
}
