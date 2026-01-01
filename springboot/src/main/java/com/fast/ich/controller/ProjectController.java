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
import com.fast.ich.domain.Project;
import com.fast.ich.service.IProjectService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 项目申报Controller
 *
 * @author fast
 * @date 2025-11-06
 */
@RestController
@RequestMapping("/ich/project")
public class ProjectController extends BaseController {
    @Autowired
    private IProjectService projectService;

    /**
     * 查询项目申报列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Project project) {
        startPage();
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    /**
     * 导出项目申报列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Project project) {
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project. class);
        util.exportExcel(response, list, "项目申报数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project. class);
        util.importTemplateExcel(response, "项目申报数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project. class);
        InputStream inputStream = file.getInputStream();
        List<Project> list = util.importExcel(inputStream);
        inputStream.close();
        int count = projectService.batchInsertProject(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取项目申报详细信息
     */
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") String projectId) {
        return success(projectService.selectProjectByProjectId(projectId));
    }

    /**
     * 新增项目申报
     */
    @PostMapping
    public AjaxResult add(@RequestBody Project project) {
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改项目申报
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Project project) {
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除项目申报
     */
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable String[] projectIds) {
        return toAjax(projectService.deleteProjectByProjectIds(projectIds));
    }
}
