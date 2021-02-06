package com.code.community.common.validator.constraint;

import com.code.community.common.validator.annotion.IdValid;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<IdValid, String> {

    @Override
    public void initialize(IdValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        if (id != null || !StringUtils.isEmpty(id.trim()) ) {
            String rex = "^\\d{19}$";
            return id.trim().matches(rex);
        }
        return false;
    }


}
