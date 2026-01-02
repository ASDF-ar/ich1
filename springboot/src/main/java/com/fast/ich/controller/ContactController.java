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
import com.fast.ich.domain.Contact;
import com.fast.ich.service.IContactService;
import com.fast.system.general.utils.poi.ExcelUtil;
import com.fast.system.general.core.page.TableDataInfo;

/**
 * 留言反馈Controller
 *
 * @author fast
 * @date 2025-11-07
 */
@RestController
@RequestMapping("/ich/contact")
public class ContactController extends BaseController {
    @Autowired
    private IContactService contactService;

    /**
     * 查询留言反馈列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Contact contact) {
        startPage();
        List<Contact> list = contactService.selectContactList(contact);
        return getDataTable(list);
    }

    /**
     * 导出留言反馈列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, Contact contact) {
        List<Contact> list = contactService.selectContactList(contact);
        ExcelUtil<Contact> util = new ExcelUtil<Contact>(Contact. class);
        util.exportExcel(response, list, "留言反馈数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Contact> util = new ExcelUtil<Contact>(Contact. class);
        util.importTemplateExcel(response, "留言反馈数据");
    }

    /**
     * 导入数据
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Contact> util = new ExcelUtil<Contact>(Contact. class);
        InputStream inputStream = file.getInputStream();
        List<Contact> list = util.importExcel(inputStream);
        inputStream.close();
        int count = contactService.batchInsertContact(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取留言反馈详细信息
     */
    @GetMapping(value = "/{contactId}")
    public AjaxResult getInfo(@PathVariable("contactId") String contactId) {
        return success(contactService.selectContactByContactId(contactId));
    }

    /**
     * 新增留言反馈
     */
    @PostMapping
    public AjaxResult add(@RequestBody Contact contact) {
        return toAjax(contactService.insertContact(contact));
    }

    /**
     * 修改留言反馈
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Contact contact) {
        return toAjax(contactService.updateContact(contact));
    }

    /**
     * 删除留言反馈
     */
    @DeleteMapping("/{contactIds}")
    public AjaxResult remove(@PathVariable String[] contactIds) {
        return toAjax(contactService.deleteContactByContactIds(contactIds));
    }
}
