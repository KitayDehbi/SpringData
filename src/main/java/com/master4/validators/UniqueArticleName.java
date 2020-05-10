package com.master4.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.FIELD )
@Constraint(validatedBy = UniqueArticleNameClass.class)
public @interface UniqueArticleName {
    String message() default "the title is already used ";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
