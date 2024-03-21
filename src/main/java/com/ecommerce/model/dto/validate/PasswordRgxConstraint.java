package com.ecommerce.model.dto.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordRgxValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRgxConstraint {
    String message() default "Password cần có ít nhất 8 ký tự hoa, thường, số và 1 trong những ký tự [@#%$^]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
