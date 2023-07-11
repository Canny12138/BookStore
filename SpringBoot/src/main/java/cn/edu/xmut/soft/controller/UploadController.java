package cn.edu.xmut.soft.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import cn.edu.xmut.springboot.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    String base = "D:\\C\\桌面\\毕设\\项目\\BookStore\\Vue\\public\\img\\"; // 不要使用项目内的目录

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/save")
    @ResponseBody
    public Result save(MultipartFile file, Map<String, Object> map) throws IOException {
        Result result = new Result();
        if (file.getSize() > 0) {
            String origName = file.getOriginalFilename();
            String saveName = UUID.randomUUID() + "." + origName.split("[.]")[1];
            File files = new File(base + saveName);
            file.transferTo(files);
            result.setData(saveName);
            return result;
        }
        return result;
    }

//    @PostMapping("/save-all")
//    public String saveAll(MultipartFile[] upfiles, String title, Map<String, Object> map) throws IOException {
//
//        map.put("title", title);
//        int saveCount = 0;
//        if (upfiles.length > 0) {
//            MultipartFile upfile;
//            for (int i = 0; i < upfiles.length; i++) {
//                upfile = upfiles[i];
//
//                if (upfile.getSize() > 0) {
//                    saveCount++;
//                    String origName = upfile.getOriginalFilename();
//                    String saveName = UUID.randomUUID() + origName;
//                    File file = new File(base + saveName);
//                    upfile.transferTo(file);
//                }
//            }
//        }
//
//        return saveCount > 0 ? "success" : "failure";
//    }
}