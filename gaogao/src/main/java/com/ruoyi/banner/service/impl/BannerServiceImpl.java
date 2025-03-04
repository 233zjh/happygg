package com.ruoyi.banner.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.banner.mapper.BannerMapper;
import com.ruoyi.banner.domain.Banner;
import com.ruoyi.banner.service.IBannerService;

/**
 * 轮播图Service业务层处理
 *
 * @author zjh
 * @date 2024-12-26
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService
{
    @Autowired
    private BannerMapper bannerMapper;

    private QueryWrapper<Banner> getWrapper(Banner params){
        QueryWrapper<Banner> queryWrapper = new QueryWrapper<>();
        if (params != null){
            queryWrapper.like(StringUtils.isNotBlank(params.getBannerName()), "banner_name", params.getBannerName());
            queryWrapper.eq(params.getIsHidden()!=null, "is_hidden", params.getIsHidden());
        }
        return queryWrapper;
    }

    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    @Override
    public Banner selectBannerById(Long id)
    {
        return bannerMapper.selectById(id);
    }

    /**
     * 查询轮播图列表
     *
     * @param banner 轮播图
     * @return 轮播图
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectList(getWrapper(banner));
    }

    /**
     * 新增轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setIsHidden(0L);
        return bannerMapper.insert(banner);
    }

    /**
     * 修改轮播图
     *
     * @param banner 轮播图
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        return bannerMapper.updateById(banner);
    }

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] ids)
    {
        return bannerMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long id)
    {
        return bannerMapper.deleteById(id);
    }
}
