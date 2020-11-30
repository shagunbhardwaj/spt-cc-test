package com.spt.cctest.api.model.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NotNull
@Valid
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidLimitAmountValidator.class })
@Documented
public @interface ValidLimitAmount {
    String message() default "must not be negative";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
