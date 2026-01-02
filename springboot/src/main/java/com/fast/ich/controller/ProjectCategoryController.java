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
import com.fast.ich.domain.ProjectCategory;
import com.fast.ich.service.IProjectCategoryService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 项目申报类别Controller
 *
 * @author fast
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/ich/category")
public class ProjectCategoryController extends BaseController {
    @Autowired
    private IProjectCategoryService projectCategoryService;

    /**
     * 查询项目申报类别列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProjectCategory projectCategory) {
        startPage();
        List<ProjectCategory> list = projectCategoryService.selectProjectCategoryList(projectCategory);
        return getDataTable(list);
    }

    /**
     * 导出项目申报类别列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectCategory projectCategory) {
        List<ProjectCategory> list = projectCategoryService.selectProjectCategoryList(projectCategory);
        ExcelUtil<ProjectCategory> util = new ExcelUtil<ProjectCategory>(ProjectCategory. class);
        util.exportExcel(response, list, "项目申报类别数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<ProjectCategory> util = new ExcelUtil<ProjectCategory>(ProjectCategory. class);
        util.importTemplateExcel(response, "项目申报类别数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ProjectCategory> util = new ExcelUtil<ProjectCategory>(ProjectCategory. class);
        InputStream inputStream = file.getInputStream();
        List<ProjectCategory> list = util.importExcel(inputStream);
        inputStream.close();
        int count = projectCategoryService.batchInsertProjectCategory(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取项目申报类别详细信息
     */
    @GetMapping(value = "/{pcId}")
    public AjaxResult getInfo(@PathVariable("pcId") String pcId) {
        return success(projectCategoryService.selectProjectCategoryByPcId(pcId));
    }

    /**
     * 新增项目申报类别
     */
    @PostMapping
    public AjaxResult add(@RequestBody ProjectCategory projectCategory) {
        return toAjax(projectCategoryService.insertProjectCategory(projectCategory));
    }

    /**
     * 修改项目申报类别
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectCategory projectCategory) {
        return toAjax(projectCategoryService.updateProjectCategory(projectCategory));
    }

    /**
     * 删除项目申报类别
     */
    @DeleteMapping("/{pcIds}")
    public AjaxResult remove(@PathVariable String[] pcIds) {
        return toAjax(projectCategoryService.deleteProjectCategoryByPcIds(pcIds));
    }

    /**
     * 查询所有项目申报类别
     */
    @GetMapping("/selectAllCategoryList")
    public AjaxResult selectAllCategoryList() {
        return success(projectCategoryService.selectProjectCategoryList(new ProjectCategory()));
    }
}
