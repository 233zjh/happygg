package com.ruoyi.categoryItem.service.impl;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import com.ruoyi.categoryItem.domain.CategoryItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.categoryItem.mapper.CategoryItemMapper;
import com.ruoyi.categoryItem.service.ICategoryItemService;
import org.springframework.stereotype.Service;

/**
 * 图片视频-分类中间表Service接口
 *
 * @author zjh
 * @date 2024-12-30
 */
@Service
public class CategoryItemServiceImpl extends ServiceImpl<CategoryItemMapper, CategoryItem> implements ICategoryItemService{

    @Autowired
    private CategoryItemMapper categoryItemMapper;

    private QueryWrapper<CategoryItem> getWrapper(CategoryItem params){
        QueryWrapper<CategoryItem> queryWrapper = new QueryWrapper<>();
        if (params != null){

        }
        return queryWrapper;
    }

    /**
     * 查询图片视频-分类中间表列表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 图片视频-分类中间表集合
     */
    @Override
    public List<CategoryItem> selectCategoryItemList(CategoryItem categoryItem) {
        return categoryItemMapper.selectList(getWrapper(categoryItem));
    }

    /**
     * 新增图片视频-分类中间表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 结果
     */
    @Override
    public int insertCategoryItem(CategoryItem categoryItem) {

        return categoryItemMapper.insert(categoryItem);
    }

    /**
     * 修改图片视频-分类中间表
     *
     * @param categoryItem 图片视频-分类中间表
     * @return 结果
     */
    @Override
    public int updateCategoryItem(CategoryItem categoryItem) {
        return categoryItemMapper.updateById(categoryItem);
    }

    /**
     * 批量删除图片视频-分类中间表
     *
     * @param ids 需要删除的图片视频-分类中间表主键集合
     * @return 结果
     */
    @Override
    public int deleteCategoryItemByIds(Long[] ids) {
        return categoryItemMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除图片视频-分类中间表信息
     *
     * @param id 图片视频-分类中间表主键
     * @return 结果
     */
    @Override
    public int deleteCategoryItemById(Long id) {
        return categoryItemMapper.deleteById(id);
    }

    /**
     * 获取图片视频-分类中间表信息
     *
     * @param id 图片视频-分类中间表主键
     * @return 结果
     */
    @Override
    public CategoryItem selectCategoryItemById(Long id) {
        return categoryItemMapper.selectById(id);
    }
}
