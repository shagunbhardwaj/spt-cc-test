package com.spt.cctest.api.model;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks for valid card numbers as per Luhn 10 algo.
 *
 */
public class ValidCardNumberValidator implements ConstraintValidator<ValidCardNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isValid(s);
    }

    private boolean isValid(String cardNumber) {
        if (cardNumber.length() < 11 || cardNumber.length() > 19)
            return false;
        char[] charArr = cardNumber.toCharArray();
        for (int i = charArr.length-1 ; i > -1 ; i-- ) {
            if (Character.isLetter(charArr[i]))
                return false;
            if ((charArr.length - i) % 2 == 0  ) {
                int t = Character.getNumericValue(charArr[i])*2;
                if (t > 9) {
                    t = t - 9 ;
                }
                charArr[i] = (char)t;
            }
        }
        int sum = 0;
        int i = 0 ;
        while ( i < charArr.length) {
            int v = Character.getNumericValue(charArr[i]) < 0 ? (int)charArr[i] : Character.getNumericValue(charArr[i]);
            sum = sum + v;
            i++;
        }
        if (sum % 10 == 0 )
            return true;
        return false;
    }

}
