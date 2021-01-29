package com.pyc.campus.service.impl;

import com.pyc.campus.dao.QuestionRepository;
import com.pyc.campus.domain.Question;
import com.pyc.campus.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file QuestionServiceImpl
 * @pack com.pyc.campus.service.impl
 * @date 2021/1/29
 * @time 15:00
 * @E-mail 2923616405@qq.com
 **/

@Service
public class QuestionServiceImpl implements QuestionService {

    final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Page<Question> getQuestionList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> getQuestionListByType(int pageNum, int pageSize, String type) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return questionRepository.findAllByType(type,pageable);
    }
}
