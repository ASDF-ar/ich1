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
import com.fast.ich.domain.Heritage;
import com.fast.ich.service.IHeritageService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 非遗项目Controller
 *
 * @author fast
 * @date 2025-12-24
 */
@RestController
@RequestMapping("/ich/heritage")
public class HeritageController extends BaseController {
    @Autowired
    private IHeritageService heritageService;

    /**
     * 查询非遗项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Heritage heritage) {
        startPage();
        List<Heritage> list = heritageService.selectHeritageList(heritage);
        return getDataTable(list);
    }

    /**
     * 导出非遗项目列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Heritage heritage) {
        List<Heritage> list = heritageService.selectHeritageList(heritage);
        ExcelUtil<Heritage> util = new ExcelUtil<Heritage>(Heritage.class);
        util.exportExcel(response, list, "非遗项目数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Heritage> util = new ExcelUtil<Heritage>(Heritage.class);
        util.importTemplateExcel(response, "非遗项目数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Heritage> util = new ExcelUtil<Heritage>(Heritage.class);
        InputStream inputStream = file.getInputStream();
        List<Heritage> list = util.importExcel(inputStream);
        inputStream.close();
        int count = heritageService.batchInsertHeritage(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取非遗项目详细信息
     */
    @GetMapping(value = "/{heritageId}")
    public AjaxResult getInfo(@PathVariable("heritageId") String heritageId) {
        return success(heritageService.selectHeritageByHeritageId(heritageId));
    }

    /**
     * 新增非遗项目
     */
    @PostMapping
    public AjaxResult add(@RequestBody Heritage heritage) {
        return toAjax(heritageService.insertHeritage(heritage));
    }

    /**
     * 修改非遗项目
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Heritage heritage) {
        return toAjax(heritageService.updateHeritage(heritage));
    }

    /**
     * 删除非遗项目
     */
    @DeleteMapping("/{heritageIds}")
    public AjaxResult remove(@PathVariable String[] heritageIds) {
        return toAjax(heritageService.deleteHeritageByHeritageIds(heritageIds));
    }

    /**
     * 提取唯一分类字段并返回给前端
     */
    @GetMapping("/selectOnlyCategories")
    public AjaxResult selectOnlyCategories() {
        List<String> categories = heritageService.selectOnlyCategories();
        return AjaxResult.success(categories);

    }
}
