package com.logicerror.e_learning.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueFieldsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueFields {
    String message() default "Duplicate titles or orders are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] fields();

    String collectionProperty();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        UniqueFields[] value();
    }
}
