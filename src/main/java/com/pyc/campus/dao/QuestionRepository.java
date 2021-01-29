//IntelliJ IDEA
//campus
//QuestionRepository
//2020/6/14
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.dao;

import com.pyc.campus.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAll();
    Page<Question> findAllByType(String type, Pageable p);
}
