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
import com.fast.ich.domain.Event;
import com.fast.ich.service.IEventService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 活动预告Controller
 *
 * @author fast
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/ich/event")
public class EventController extends BaseController {
    @Autowired
    private IEventService eventService;

    /**
     * 查询活动预告列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Event event) {
        startPage();
        List<Event> list = eventService.selectEventList(event);
        return getDataTable(list);
    }

    /**
     * 导出活动预告列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Event event) {
        List<Event> list = eventService.selectEventList(event);
        ExcelUtil<Event> util = new ExcelUtil<Event>(Event. class);
        util.exportExcel(response, list, "活动预告数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Event> util = new ExcelUtil<Event>(Event. class);
        util.importTemplateExcel(response, "活动预告数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Event> util = new ExcelUtil<Event>(Event. class);
        InputStream inputStream = file.getInputStream();
        List<Event> list = util.importExcel(inputStream);
        inputStream.close();
        int count = eventService.batchInsertEvent(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取活动预告详细信息
     */
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") String eventId) {
        return success(eventService.selectEventByEventId(eventId));
    }

    /**
     * 新增活动预告
     */
    @PostMapping
    public AjaxResult add(@RequestBody Event event) {
        return toAjax(eventService.insertEvent(event));
    }

    /**
     * 修改活动预告
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Event event) {
        return toAjax(eventService.updateEvent(event));
    }

    /**
     * 删除活动预告
     */
    @DeleteMapping("/{eventIds}")
    public AjaxResult remove(@PathVariable String[] eventIds) {
        return toAjax(eventService.deleteEventByEventIds(eventIds));
    }

    /**
     * 查询所有大于当前日期的活动预告列表, 并根据日期正序排序
     */
    @GetMapping("/selectGreaterThanTheCurrentDateEventList")
    public AjaxResult selectGreaterThanTheCurrentDateEventList () {
        return success(eventService.selectGreaterThanTheCurrentDateEventList());
    }
}
