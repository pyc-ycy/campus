//IntelliJ IDEA
//campus
//NewsRepository
//2020/6/10
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.dao;

import com.pyc.campus.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
