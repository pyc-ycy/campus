package com.pyc.campus.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file CsvJobListener
 * @pack com.pyc.campus.batch
 * @date 2021/1/17
 * @time 12:59
 * @E-mail 2923616405@qq.com
 **/


public class CsvJobListener implements JobExecutionListener {
    long startTime;
    long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        System.out.println("任务处理开始");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        System.out.println("任务处理结束");
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }
}
