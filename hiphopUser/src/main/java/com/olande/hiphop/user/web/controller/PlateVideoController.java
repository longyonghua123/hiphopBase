package com.olande.hiphop.user.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.olande.common.entity.JSONData;
import com.olande.common.entity.PageData;
import com.olande.common.util.DES;
import com.olande.common.util.FileUtils;
import com.olande.hiphop.entity.PlateVideo;
import com.olande.hiphop.service.IPlateVideoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("video")
@Api(value = "视频")
public class PlateVideoController extends BaseController {
    /**
     * 上传文件基本路径
     */
    @Value("${upload.dir.path}")
    private String uploadDirPath;
    @Autowired
    @Qualifier("plateVideoService")
    private IPlateVideoService plateVideoService;

    /**
     * 发布视频
     *
     * @param video
     * @return
     */
    @PostMapping(value = "pub_video")
    public String  pubVideo(PlateVideo video, HttpSession session) {
        String memberId = getMemberUserId(session);
        video.setMemberUserId(memberId);
        int status = plateVideoService.insertVideo(video);
        return getReturnPath(false, status, "/page/common/add_result", "/page/video/pub_video");

    }

    /**
     * 根据视频ID查询视频详细信息
     *
     * @param videoId
     * @return
     */
    @GetMapping("get_video/{id}")
    @ResponseBody
    public JSONData getVideo(@PathVariable("id") String videoId) {
        PlateVideo video = plateVideoService.getVideo(videoId);
        return JSONData.SUCCESS(video);
    }

    /**
     * 根据关键字查询视频
     *
     * @param keyword
     * @return
     */
    @GetMapping(value = "search_by_kw")
    @ResponseBody
    public JSONData selectVideoByKW(@RequestParam String keyword) {
        List<PlateVideo> videoList = plateVideoService.selectVideoByKW(1, keyword);
        return JSONData.SUCCESS(videoList);
    }


    /**
     * 根据分类代码查询视频
     *
     * @param dictCode 分类字典代码(字典二级代码)
     * @return
     */
    @PostMapping("search_by_type")
    @ResponseBody
    public JSONData selectVideoByType(@RequestParam("dict_code") String dictCode) {
        List<PlateVideo> videoList = plateVideoService.selectVideoByType(1, dictCode);
        return JSONData.SUCCESS(videoList);

    }

    /**
     * 根据会员用户ID查询当前用户所有视频
     *
     * @param memberId
     * @return
     */
    @PostMapping("search_by_member")
    @ResponseBody
    public JSONData selectVideoByMember(@RequestParam("member_id") String memberId) {
        List<PlateVideo> videoList = plateVideoService.selectVideoByMember(memberId);
        return JSONData.SUCCESS(videoList);
    }

    /**
     * 上架或下架视频
     *
     * @param videoId     视频ID
     * @param videoStatus 1:上架;2:下架
     * @return
     */
    @GetMapping("set_status")
    @ResponseBody
    public JSONData setStatus(@RequestParam("video_id") String videoId, @RequestParam("video_status") int videoStatus) {
        int status = plateVideoService.updateVideoStatus(videoId, 0, videoStatus);
        return JSONData.SUCCESS(status);
    }

    /**
     * 根据视频ID删除视频
     * 说明:审核中不可以删除
     *
     * @param videoId
     * @return
     */
    @GetMapping("delete_by_id")
    @ResponseBody
    public JSONData deleteVideo(@RequestParam("video_id") String videoId) {
        int status = plateVideoService.deleteVideo(videoId);
        return JSONData.SUCCESS(status);
    }

    /**
     * 视频上传
     *
     * @param videoFile
     * @param
     * @return
     */
    @PostMapping("upload")
    @ResponseBody
    public JSONData addFile(@RequestParam("video") MultipartFile videoFile, HttpSession session) throws Exception {
        if (videoFile.isEmpty()) {
            return JSONData.FAIL(100400, "没有可以上传的文件");
        }
        //文件名称
        String fileName = videoFile.getOriginalFilename();
        if (!FileUtils.isVideoFile(fileName)) {
            return JSONData.FAIL(100402, "文件格式错误,只可以上传视频文件");
        }
        String memberId = getMemberUserId(session);
        //生成本地视频相对路径文件夹:/用户ID/yyyyDDD(四位年份+三位年中的天)
        String relativeDirPath = memberId + File.separator + DateUtil.format(new Date(), "yyyyDDD");
        File uploadDir = new File(uploadDirPath, relativeDirPath);
        FileUtil.mkdir(uploadDir);
        File uploadFile = new File(uploadDir, fileName);
        if (!uploadFile.isFile())//判断文件夹是否存在
            uploadFile.createNewFile();
        //把视频存储在本地文件夹
        videoFile.transferTo(uploadFile);
        //绝对路径
        String videoFilePath = uploadFile.getPath();
        //DES加密
        String videoUrl = DES.encode(DES.KEY, videoFilePath);
        return JSONData.SUCCESS(videoUrl);
    }

    /**
     * Java生成Blob二进制流
     *
     * @param videoUrl 视频地址:绝对路径DES加密
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("get_blob")
    public void getVideoBlob(@RequestParam("video_url") String videoUrl, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String videoFilePath = DES.decode(DES.KEY, videoUrl);
        File videoFile = new File(videoFilePath);
        String videoFileName = videoFile.getName();
        if (!videoFile.isFile()) {
            throw new Exception("视频文件不存在");
        }
        String agent = request.getHeader("User-Agent").toUpperCase();
        BufferedInputStream bufInput = null;
        OutputStream out = null;
        try {
            bufInput = new BufferedInputStream(new FileInputStream(videoFile));
            byte[] buffer;
            buffer = new byte[bufInput.available()];
            bufInput.read(buffer);
            response.reset();
            //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
            if (agent.indexOf("FIREFOX") != -1) {//火狐浏览器
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(videoFileName.getBytes("GB2312"), "ISO-8859-1"));
            } else {//其他浏览器
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(videoFileName, "UTF-8"));
            }
            //设置response编码
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Length", "" + videoFile.length());
            //设置输出文件类型
            response.setContentType("video/mpeg4");
            //获取response输出流
            out = response.getOutputStream();
            // 输出文件
            out.write(buffer);
        } catch (Exception e) {

        } finally {
            //关闭流
            if (bufInput != null) {
                bufInput.close();
            }
        }
    }

    /**
     * 分页查询我的视频
     *
     * @return
     */
    @RequestMapping(value = "my_video_page", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryMyVideoByPage(HttpServletRequest request, PageData<PlateVideo> page) {
        setPageQueryConfig(request, page);
        String memberId = getMemberUserId(request.getSession());
        //添加用户ID作为查询条件
        page.getCriteriaMap().put("member_user_id", memberId);
        page = plateVideoService.selectVideoByPage(page);
        request.setAttribute("page", page);
        return "user/my_video";
    }

    /**
     * 观看视频加载视频信息
     *
     * @param videoId
     * @param model
     * @return
     */
    @GetMapping("{video_id}.html")
    public String watchVideo(@PathVariable("video_id") String videoId, Model model) {
        Map<String, Object> videoMap = plateVideoService.getVideoMap(videoId);
        System.out.println(videoMap.toString());
        model.addAttribute("videoMap", videoMap);
        return "video/video_view";
    }

    /**
     * 分页查询视频
     *
     * @return
     */
    @RequestMapping(value = "search", method = {RequestMethod.POST, RequestMethod.GET})
    public String searchVideo(HttpServletRequest request, PageData<PlateVideo> page) {
        setPageQueryConfig(request, page);
        page.getCriteriaMap().put("video_status","1");
        page = plateVideoService.selectVideoByPage(page);
        request.setAttribute("page", page);
        return "video/video_search_list";
    }
}
