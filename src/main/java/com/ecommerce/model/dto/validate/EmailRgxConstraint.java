package com.ecommerce.model.dto.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailRgxValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailRgxConstraint {
    String message() default "Định dạng email không chính xác";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
