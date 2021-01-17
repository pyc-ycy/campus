package com.pyc.campus.batch;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Errors;
import org.springframework.batch.item.validator.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file CsvBeanValidator
 * @pack com.pyc.campus.batch
 * @date 2021/1/17
 * @time 12:42
 * @E-mail 2923616405@qq.com
 **/


public class CsvBeanValidator<T> implements Validator<T>, InitializingBean {
    private javax.validation.Validator validator;

    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public void validate(T t) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if(constraintViolations.size()>0){
            StringBuilder message = new StringBuilder();
            for(ConstraintViolation<T> constraintViolation : constraintViolations){
                message.append(constraintViolation.getMessage()).append("\n");
            }
            throw new ValidationException(message.toString());
        }
    }
}
