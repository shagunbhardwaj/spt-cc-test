package com.spt.cctest.api.model;

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
@Constraint(validatedBy = { ValidCardNumberValidator.class })
@Documented
public @interface ValidCardNumber {
    String message() default "card number must be valid with length 11 - 19 digits";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
