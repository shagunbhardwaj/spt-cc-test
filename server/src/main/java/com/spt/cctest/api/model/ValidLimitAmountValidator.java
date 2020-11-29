package com.spt.cctest.api.model;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLimitAmountValidator implements ConstraintValidator<ValidLimitAmount, Money> {
    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        return BigDecimal.ZERO.compareTo(money.getAmount()) < 0;
    }
}
