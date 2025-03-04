package com.ruoyi.banner.controller;

import com.ruoyi.banner.domain.Banner;
import com.ruoyi.banner.service.IBannerService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 轮播图Controller
 *
 * @author zjh
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/api/banner")
public class BannerAPIController extends BaseController
{
    @Autowired
    private IBannerService bannerService;

    /**
     * 查询轮播图列表
     */
    @GetMapping("/list")
    public AjaxResult list(Banner banner)
    {
        banner.setIsHidden(0L);
        List<Banner> list = bannerService.selectBannerList(banner);
        list.forEach(item -> {
            item.setImage("http://localhost/dev-api"+item.getUrl());
        });
        return AjaxResult.success(list);
    }

    public static void main(String[] args) {
        System.out.println(RuoYiConfig.getProfile());
        System.out.println(RuoYiConfig.getUploadPath());
    }

    /**
     * 导出轮播图列表
     */
    @Log(title = "轮播图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Banner banner)
    {
        List<Banner> list = bannerService.selectBannerList(banner);
        ExcelUtil<Banner> util = new ExcelUtil<Banner>(Banner.class);
        util.exportExcel(response, list, "轮播图数据");
    }

    /**
     * 获取轮播图详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bannerService.selectBannerById(id));
    }

    /**
     * 新增轮播图
     */
    @Log(title = "轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner)
    {
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 修改轮播图
     */
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner)
    {
        return toAjax(bannerService.updateBanner(banner));
    }

    /**
     * 删除轮播图
     */
    @Log(title = "轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bannerService.deleteBannerByIds(ids));
    }

    /**
     * 隐藏分类
     */
    @Log(title = "轮播图", businessType = BusinessType.UPDATE)
    @PostMapping("/hidden")
    public AjaxResult hidden(@RequestBody Banner banner)
    {
        return toAjax(bannerService.updateBanner(banner));
    }
}
