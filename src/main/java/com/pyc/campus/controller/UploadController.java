package com.pyc.campus.controller;

import com.pyc.campus.domain.Msg;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    final
    JobLauncher jobLauncher;
    final Job importJob;
    public JobParameters jobParameters;

    public UploadController(JobLauncher jobLauncher,Job importJob) {
        this.jobLauncher = jobLauncher;
        this.importJob = importJob;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public  String upload(MultipartFile file, Model model){
        try{
            String path = "D:\\users\\lenovo\\Spring项目\\campus\\upLoadFilesSet\\"+file.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(path),
                    file.getBytes());
            jobParameters = new JobParametersBuilder()
                    .addLong("time",System.currentTimeMillis())
                    .addString("input.file.name",path)
                    .toJobParameters();
            jobLauncher.run(importJob, jobParameters);
            Msg msg = new Msg("系统提示","导入成功","");
            model.addAttribute("msg",msg);
            return "page/ImportGrade";
        }catch (Exception e){
            e.printStackTrace();
            Msg msg = new Msg("系统提示","导入失败","");
            model.addAttribute("msg",msg);
            return "page/ImportGrade";
        }
    }
}
