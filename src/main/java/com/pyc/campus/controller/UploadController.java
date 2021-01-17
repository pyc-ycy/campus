package com.pyc.campus.controller;

import com.pyc.campus.domain.Msg;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file UploadController
 * @pack com.pyc.campus.controller
 * @date 2021/1/17
 * @time 11:23
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class UploadController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public  String upload(MultipartFile file, Model model){
        try{
            FileUtils.writeByteArrayToFile(new File("D:\\users\\lenovo\\Spring项目\\campus\\upLoadFilesSet\\"+file.getOriginalFilename()),
                    file.getBytes());
            Msg msg = new Msg("系统提示","导入成功","");
            model.addAttribute("msg",msg);
            return "page/ImportGrade";
        }catch (IOException e){
            e.printStackTrace();
            Msg msg = new Msg("系统提示","导入失败","");
            model.addAttribute("msg",msg);
            return "page/ImportGrade";
        }
    }
}
