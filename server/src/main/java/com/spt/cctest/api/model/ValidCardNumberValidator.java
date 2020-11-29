package com.spt.cctest.api.model;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCardNumberValidator implements ConstraintValidator<ValidCardNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isValid(s);
    }

    private boolean isValid(String cardNumber) {
        char[] charArr = cardNumber.toCharArray();
        for (int i = charArr.length-2 ; i > -1 ; i =-2 ) {
            int t = Character.getNumericValue(charArr[i])*2;
            if (t > 9) {
                String temp = ""+t;
                t = Character.getNumericValue(temp.charAt(0)) + Character.getNumericValue(temp.charAt(1));
            }
            charArr[i] = (char)t;
        }
        int sum = 0;
        for (char c : charArr) {
            sum += Character.getNumericValue(c);
        }
        if (sum % 10 == 0 )
            return true;
        return false;
    }

}
