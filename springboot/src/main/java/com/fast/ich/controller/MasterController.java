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
import com.fast.ich.domain.Master;
import com.fast.ich.service.IMasterService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 传承人Controller
 *
 * @author fast
 * @date 2025-11-06
 */
@RestController
@RequestMapping("/ich/master")
public class MasterController extends BaseController {
    @Autowired
    private IMasterService masterService;

    /**
     * 查询传承人列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Master master) {
        startPage();
        List<Master> list = masterService.selectMasterList(master);
        return getDataTable(list);
    }

    /**
     * 导出传承人列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Master master) {
        List<Master> list = masterService.selectMasterList(master);
        ExcelUtil<Master> util = new ExcelUtil<Master>(Master. class);
        util.exportExcel(response, list, "传承人数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Master> util = new ExcelUtil<Master>(Master. class);
        util.importTemplateExcel(response, "传承人数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Master> util = new ExcelUtil<Master>(Master. class);
        InputStream inputStream = file.getInputStream();
        List<Master> list = util.importExcel(inputStream);
        inputStream.close();
        int count = masterService.batchInsertMaster(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取传承人详细信息
     */
    @GetMapping(value = "/{masterId}")
    public AjaxResult getInfo(@PathVariable("masterId") String masterId) {
        return success(masterService.selectMasterByMasterId(masterId));
    }

    /**
     * 新增传承人
     */
    @PostMapping
    public AjaxResult add(@RequestBody Master master) {
        return toAjax(masterService.insertMaster(master));
    }

    /**
     * 修改传承人
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Master master) {
        return toAjax(masterService.updateMaster(master));
    }

    /**
     * 删除传承人
     */
    @DeleteMapping("/{masterIds}")
    public AjaxResult remove(@PathVariable String[] masterIds) {
        return toAjax(masterService.deleteMasterByMasterIds(masterIds));
    }
}
