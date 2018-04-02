package cun.yun.card.admin.controller;


import cun.yun.card.admin.util.JsonResponseMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@RestController
public class FileUploadController {
    @Value("${fileUrl}")
    private String fileUrl;

    @ResponseBody
    @RequestMapping(value = "fileUp" ,method = RequestMethod.GET)
    public JsonResponseMsg fileUp(MultipartFile file){
        JsonResponseMsg result = new JsonResponseMsg();
        String filePath= "";
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                filePath = fileUrl + "/"+file.getOriginalFilename();
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.fill(JsonResponseMsg.CODE_SUCCESS,"查询成功");
    }

}