package com.olande.hiphop.admin.web.controller;

import com.olande.common.entity.JSONData;
import com.olande.hiphop.entity.SysDictItem;
import com.olande.hiphop.service.ISysDictItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 处理字典项业务请求控制器
 */
@Slf4j
@Controller
@RequestMapping("dict")
public class DictItemController extends BaseController {
    @Autowired
    @Qualifier("sysDictItemService")
    private ISysDictItemService sysDictItemService;

    /**
     * 添加主字典项
     *
     * @return
     */
    @PostMapping(value = {"add_main_dict"})
    public String addMainDict(SysDictItem dictItem, HttpSession session) {
        String addSysUserId = getSysUserId(session);
        dictItem.setAddSysUserId(addSysUserId);
        //添加字典项
        int result = sysDictItemService.insertDictItem(dictItem);
        return getReturnPath(false, result, "/page/common/add_result", "/dict/query_main_dict");
    }

    /**
     * 查询主字典项
     * 说明:如果未传值使用状态,表示查询所有,用特殊标志0
     *
     * @param useStatus 使用状态
     * @param model     数据模型对象,用来封装查询的数据
     * @return
     */
    @RequestMapping(value = "query_main_dict", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryMainDict(@RequestParam(value = "user_status", required = false) Integer useStatus, Model model) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysDictItem> dictList = sysDictItemService.queryMainDictItem(useStatus);
        model.addAttribute("dictList", dictList);
        System.out.println(dictList);
        return "/dict/main_dict_list";
    }

    /***
     * 查询主字典项名是否被使用
     *
     * @param dictName
     * @return
     */
    @RequestMapping(value = {"check_main_dict"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONData checkMainDictName(@RequestParam("dict_name") String dictName) {
        int result = sysDictItemService.checkMainDictName(dictName);
        return JSONData.SUCCESS(result);
    }

    /**
     * 查询子字典项名是否被使用
     *
     * @param dictName
     * @param pDictId
     * @return
     */
    @RequestMapping(value = {"check_sub_dict"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONData checkSubDictName(@RequestParam("dict_name") String dictName,
                                     @RequestParam("p_dict_id") String pDictId) {
        int result = sysDictItemService.checkSubDictName(dictName, pDictId);
        return JSONData.SUCCESS(result);
    }


    /**
     * 修改主字典项
     *
     * @return
     */
    @RequestMapping(value = {"update_main_dict"}, method = {RequestMethod.POST})
    public String updateMainDict(SysDictItem dict, HttpSession session) {
        String updateSysUserId = getSysUserId(session);
        dict.setUpdateSysUserId(updateSysUserId);
        int result = sysDictItemService.updateDictItem(dict);
        return getReturnPath(false, result, "/page/common/update_result", "/dict/query_main_dict");
    }

    /**
     * 修改子字典项
     *
     * @return
     */
    @RequestMapping(value = {"update_sub_dict"}, method = {RequestMethod.POST})
    public String updateSubDict(SysDictItem dict, HttpSession session) {
        String updateSysUserId = getSysUserId(session);
        dict.setUpdateSysUserId(updateSysUserId);
        int result = sysDictItemService.updateDictItem(dict);
        return getReturnPath(false, result, "/page/common/update_result", "/page/dict/sub_dict_list?p_dict_code=" + dict.getPDictCode());

    }
    /**
     * 设置主字典项状态
     *
     * @return
     */
    @GetMapping(value = "set_dict_status")
    public String setUseStatus(@RequestParam("use_status") int useStatus, @RequestParam("dict_id") String dictId, HttpSession session) {
        String sysUserId = getSysUserId(session);
        int result = sysDictItemService.updateUseStatus(useStatus, dictId, sysUserId);
        return getReturnPath(false, result, "/page/common/update_result", "/dict/query_main_dict");
    }

    /**
     * 设置主字典项状态
     *
     * @return
     */
    @GetMapping(value = "set_sub_dict_status")
    public String setUseStatus(@RequestParam("use_status") int useStatus, @RequestParam("dict_id") String dictId, @RequestParam("p_dict_code") String pDictCode, HttpSession session) {
        String sysUserId = getSysUserId(session);
        int result = sysDictItemService.updateUseStatus(useStatus, dictId, sysUserId);
        return getReturnPath(false, result, "/page/common/update_result", "/page/dict/sub_dict_list?p_dict_code=" + pDictCode);
    }


    /**
     * 加载主字典项
     *
     * @return
     */
    @RequestMapping({"load_main_dict"})
    @ResponseBody
    public JSONData loadMainDict(@RequestParam(value = "use_status", required = false) Integer useStatus) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysDictItem> dictList = sysDictItemService.queryMainDictItem(useStatus);
        return JSONData.SUCCESS(dictList);
    }


    /**
     * 添加子字典项
     *
     * @return
     */
    @PostMapping(value = "add_sub_dict")
    public String addSubDict(SysDictItem dict, HttpSession session) {
        String addSysUserId = getSysUserId(session);
        dict.setAddSysUserId(addSysUserId);
        //添加字典项
        int result = sysDictItemService.insertDictItem(dict);
        return getReturnPath(false, result, "/page/common/add_result", "/page/dict/sub_dict_list?p_dict_code=" + dict.getPDictCode());
    }


    /**
     * 加载子字典项数据
     *
     * @param pDictCode
     * @param useStatus
     * @return
     */
    @GetMapping(value = "load_sub_dict")
    @ResponseBody
    public JSONData loadSubDict(@RequestParam("p_dict_code") String pDictCode, @RequestParam(value = "use_status", required = false) Integer useStatus) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysDictItem> dictList = sysDictItemService.querySubDictItem(useStatus, pDictCode);
        return JSONData.SUCCESS(dictList);
    }


    /**
     * 查询子字典项数据
     *
     * @param pDictId
     * @param useStatus
     * @return
     */
    @GetMapping(value = "query_sub_dict")
    public String querySubDict(@RequestParam("p_dict_code") String pDictId, @RequestParam(value = "use_status", required = false) Integer useStatus, Model model) {
        useStatus = useStatus == null ? 0 : useStatus;
        List<SysDictItem> dictList = sysDictItemService.querySubDictItem(useStatus, pDictId);
        model.addAttribute("dictList", dictList);
        return "/dict/sub_dict_list";
    }

    /**
     * 加载子字典项数据
     *
     * @return
     */
    @GetMapping(value = "load_dict")
    @ResponseBody
    public JSONData loadDict(@RequestParam("dict_id") String dictId) {

        return JSONData.SUCCESS(sysDictItemService.getSysDictItem(dictId));
    }

}
