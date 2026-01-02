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
import com.fast.ich.domain.About;
import com.fast.ich.service.IAboutService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 关于我们Controller
 *
 * @author fast
 * @date 2025-11-07
 */
@RestController
@RequestMapping("/ich/about")
public class AboutController extends BaseController {
    @Autowired
    private IAboutService aboutService;

    /**
     * 查询关于我们列表
     */
    @GetMapping("/list")
    public TableDataInfo list(About about) {
        startPage();
        List<About> list = aboutService.selectAboutList(about);
        return getDataTable(list);
    }

    /**
     * 导出关于我们列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, About about) {
        List<About> list = aboutService.selectAboutList(about);
        ExcelUtil<About> util = new ExcelUtil<About>(About. class);
        util.exportExcel(response, list, "关于我们数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<About> util = new ExcelUtil<About>(About. class);
        util.importTemplateExcel(response, "关于我们数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<About> util = new ExcelUtil<About>(About. class);
        InputStream inputStream = file.getInputStream();
        List<About> list = util.importExcel(inputStream);
        inputStream.close();
        int count = aboutService.batchInsertAbout(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取关于我们详细信息
     */
    @GetMapping(value = "/{aboutId}")
    public AjaxResult getInfo(@PathVariable("aboutId") String aboutId) {
        return success(aboutService.selectAboutByAboutId(aboutId));
    }

    /**
     * 新增关于我们
     */
    @PostMapping
    public AjaxResult add(@RequestBody About about) {
        return toAjax(aboutService.insertAbout(about));
    }

    /**
     * 修改关于我们
     */
    @PutMapping
    public AjaxResult edit(@RequestBody About about) {
        return toAjax(aboutService.updateAbout(about));
    }

    /**
     * 删除关于我们
     */
    @DeleteMapping("/{aboutIds}")
    public AjaxResult remove(@PathVariable String[] aboutIds) {
        return toAjax(aboutService.deleteAboutByAboutIds(aboutIds));
    }

    /**
     * 查询关于我们表中有没有数据
     */
    @GetMapping("/selectIsExist")
    public AjaxResult selectIsExist() {
        int size = aboutService.selectAboutList(new About()).size();
        if (size == 0) {
            return success(false);
        } else {
            return success(true);
        }
    }

    /**
     * 查询当前的关于我们数据
     */
    @GetMapping("/selectCurrentAbout")
    public AjaxResult selectCurrentAbout() {
        About about = aboutService.selectAboutList(new About()).get(0);
        return success(about);
    }
}
