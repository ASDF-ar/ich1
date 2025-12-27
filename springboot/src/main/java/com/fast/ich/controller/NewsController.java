package com.fast.ich.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fast.system.general.core.controller.BaseController;
import com.fast.system.general.core.domain.AjaxResult;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import com.fast.ich.domain.News;
import com.fast.ich.service.INewsService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 新闻资讯Controller
 *
 * @author fast
 * @date 2025-12-27
 */
@RestController
@RequestMapping("/ich/news")
public class NewsController extends BaseController {
    @Autowired
    private INewsService newsService;

    /**
     * 查询新闻资讯列表
     */
    @GetMapping("/list")
    public TableDataInfo list(News news) {
        startPage();
        List<News> list = newsService.selectNewsList(news);
        return getDataTable(list);
    }

    /**
     * 导出新闻资讯列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, News news) {
        List<News> list = newsService.selectNewsList(news);
        ExcelUtil<News> util = new ExcelUtil<News>(News. class);
        util.exportExcel(response, list, "新闻资讯数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<News> util = new ExcelUtil<News>(News. class);
        util.importTemplateExcel(response, "新闻资讯数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<News> util = new ExcelUtil<News>(News. class);
        InputStream inputStream = file.getInputStream();
        List<News> list = util.importExcel(inputStream);
        inputStream.close();
        int count = newsService.batchInsertNews(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取新闻资讯详细信息
     */
    @GetMapping(value = "/{newsId}")
    public AjaxResult getInfo(@PathVariable("newsId") String newsId) {
        return success(newsService.selectNewsByNewsId(newsId));
    }

    /**
     * 新增新闻资讯
     */
    @PostMapping
    public AjaxResult add(@RequestBody News news) {
        return toAjax(newsService.insertNews(news));
    }

    /**
     * 修改新闻资讯
     */
    @PutMapping
    public AjaxResult edit(@RequestBody News news) {
        return toAjax(newsService.updateNews(news));
    }

    /**
     * 删除新闻资讯
     */
    @DeleteMapping("/{newsIds}")
    public AjaxResult remove(@PathVariable String[] newsIds) {
        return toAjax(newsService.deleteNewsByNewsIds(newsIds));
    }
}
