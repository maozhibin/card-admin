package cun.yun.card.admin.controller;


import cun.yun.card.admin.util.JsonResponseMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("file")
public class FileUploadController {
    @Value("${fileUrl}")
    private String fileUrl;

    @ResponseBody
    @RequestMapping(value = "fileUp" ,method = RequestMethod.POST)
    public JsonResponseMsg fileUp(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("imageFile");
        JsonResponseMsg result = new JsonResponseMsg();
        String filePath= "";
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                filePath = fileUrl + "/"+System.currentTimeMillis()+file.getOriginalFilename();
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return result.fill(JsonResponseMsg.CODE_FAIL,"请传入文件");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("filePath",filePath);
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"上传成功",map);
    }

}