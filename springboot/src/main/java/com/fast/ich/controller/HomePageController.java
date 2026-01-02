package com.fast.ich.controller;

import com.fast.ich.domain.Heritage;
import com.fast.ich.domain.Master;
import com.fast.ich.domain.News;
import com.fast.ich.domain.vo.BarVO;
import com.fast.ich.domain.vo.HomeCountVo;
import com.fast.ich.domain.vo.PieVO;
import com.fast.ich.service.IHeritageService;
import com.fast.ich.service.IMasterService;
import com.fast.ich.service.INewsService;
import com.fast.system.domain.SysUser;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;
import com.fast.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台首页Controller
 */
@RestController
@RequestMapping("/home/page")
public class HomePageController extends BaseController {
    @Autowired
    private IHeritageService heritageService;
    @Autowired
    private IMasterService masterService;
    @Autowired
    private INewsService newsService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询非遗项目数 传承人数 新闻资讯数 用户数
     */
    @GetMapping("/selectHomeCount")
    public AjaxResult selectHomeCount() {
        //非遗项目数
        int heritageCount = heritageService.selectHeritageList(new Heritage()).size();
        //传承人数
        int masterCount = masterService.selectMasterList(new Master()).size();
        //新闻资讯数
        int newsCount = newsService.selectNewsList(new News()).size();
        //用户数
        int userCount = userService.selectUserList(new SysUser()).size();

        HomeCountVo homeCountVo = new HomeCountVo();
        homeCountVo.setHeritageCount(heritageCount);
        homeCountVo.setMasterCount(masterCount);
        homeCountVo.setNewsCount(newsCount);
        homeCountVo.setUserCount(userCount);
        return AjaxResult.success(homeCountVo);
    }


    /**
     * 查询首页柱状图数据
     */
    @GetMapping("/selectBarData")
    public AjaxResult selectBarData() {
        //非遗项目数
        int heritageCount = heritageService.selectHeritageList(new Heritage()).size();
        //传承人数
        int masterCount = masterService.selectMasterList(new Master()).size();
        //新闻资讯数
        int newsCount = newsService.selectNewsList(new News()).size();
        //用户数
        int userCount = userService.selectUserList(new SysUser()).size();
        //准备要返回的数组数据
        String[] categories = {"非遗项目", "传承人", "新闻资讯", "用户"};
        Integer[] data = {heritageCount, masterCount, newsCount, userCount};
        BarVO barVO = new BarVO();
        barVO.setCategories(categories);
        barVO.setData(data);
        return success(barVO);
    }

    /**
     * 查询项目分类统计饼图数据
     */
    @GetMapping("/selectPieData")
    public AjaxResult selectPieData() {
        //查询每种项目分类各有多少个
        List<PieVO> pieVoList = heritageService.selectCategoryChart();
        return success(pieVoList);
    }

}