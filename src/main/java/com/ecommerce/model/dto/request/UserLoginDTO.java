package com.ecommerce.model.dto.request;

import com.ecommerce.model.dto.validate.EmailRgxConstraint;
import com.ecommerce.model.dto.validate.PasswordRgxConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "Trường này là bắt buộc")
    @EmailRgxConstraint
    private String email;
    @NotEmpty(message = "Trường này là bắt buộc")
    @PasswordRgxConstraint
    private String password;
}
