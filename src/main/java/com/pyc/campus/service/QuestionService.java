package com.pyc.campus.service;

import com.pyc.campus.domain.Question;
import org.springframework.data.domain.Page;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file QuestionService
 * @pack com.pyc.campus.service
 * @date 2021/1/29
 * @time 14:58
 * @E-mail 2923616405@qq.com
 **/
public interface QuestionService {

    Page<Question> getQuestionList(int pageNum, int pageSize);

    Page<Question> getQuestionListByType(int pageNum, int pageSize, String type);
}
