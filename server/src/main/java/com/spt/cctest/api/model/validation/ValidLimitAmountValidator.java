package com.spt.cctest.api.model.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.spt.cctest.api.model.Money;

public class ValidLimitAmountValidator implements ConstraintValidator<ValidLimitAmount, Money> {
    @Override
    public boolean isValid(Money money, ConstraintValidatorContext constraintValidatorContext) {
        return BigDecimal.ZERO.compareTo(money.getAmount()) < 0;
    }
}
