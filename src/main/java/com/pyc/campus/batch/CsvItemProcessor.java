package com.pyc.campus.batch;

import com.pyc.campus.domain.Grade;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file CsvItemProcessor
 * @pack com.pyc.campus.batch
 * @date 2021/1/17
 * @time 14:53
 * @E-mail 2923616405@qq.com
 **/


public class CsvItemProcessor extends ValidatingItemProcessor<Grade> {
    @Override
    public Grade process(Grade item) throws ValidationException {
        return super.process(item);
    }
}
