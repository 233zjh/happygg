package com.ruoyi.category.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.http.HttpUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.category.mapper.CategoryMapper;
import com.ruoyi.category.domain.Category;
import com.ruoyi.category.service.ICategoryService;

/**
 * 分类Service业务层处理
 *
 * @author zjh
 * @date 2024-11-15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService
{
    @Autowired
    private CategoryMapper categoryMapper;

    private QueryWrapper<Category> getWrapper(Category params){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (params != null){
            queryWrapper.like(StringUtils.isNotBlank(params.getCategoryName()), "category_name", params.getCategoryName());
            queryWrapper.eq(params.getIsHidden()!=null, "is_hidden", params.getIsHidden());
        }
        return queryWrapper;
    }

    /**
     * 查询分类
     *
     * @param id 分类主键
     * @return 分类
     */
    @Override
    public Category selectCategoryById(Long id)
    {
        return categoryMapper.selectById(id);
    }

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectList(getWrapper(category));
    }

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    @Override
    public int insertCategory(Category category)
    {
        category.setIsHidden(0L);
        return categoryMapper.insert(category);
    }

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        return categoryMapper.updateById(category);
    }

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(Long[] ids)
    {
        return categoryMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除分类信息
     *
     * @param id 分类主键
     * @return 结果
     */
    @Override
    public int deleteCategoryById(Long id)
    {
        return categoryMapper.deleteById(id);
    }

    public static void main(String[] args) {
        String access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo2NywidGltZSI6MTczODAzMTExMywibW9kdWxlIjoiY2hhbm5lbCIsImNoYW5uZWxfY29kZSI6IndlaWxhbmhhaWFuIn0.T0xsvVmARGob82GvU2CrFpKsKdJ7kUQhSB5YiPpSBr0";
        try {
            // 定义请求的URL
            URL url = new URL("https://open-partner.miot.com.cn/api/v1/channel/get_room_status?hotel_id=57844&start_date=2025-01-22&end_date=2025-01-24&room_code_list[]=RC415a1176d162471e9242ed37235fd3a1&room_code_list[]=RCd084a18e58298ff91073d17fe5bd736a");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为GET
            connection.setRequestMethod("GET");

            // 设置请求的Header
            connection.setRequestProperty("User-Agent", "MyApp/1.0");
            connection.setRequestProperty("access-token", access_token);
            connection.setRequestProperty("Accept", "application/json");

            // 发送请求
            int responseCode = connection.getResponseCode();
            System.out.println("响应码: " + responseCode);

            // 读取响应内容
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                // 打印响应内容
                System.out.println("响应内容: " + StringEscapeUtils.unescapeJava(response.toString()));
            } else {
                // 打印错误信息
                System.out.println("请求失败，状态码: " + responseCode);
                System.out.println("错误信息: " + connection.getResponseMessage());
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
