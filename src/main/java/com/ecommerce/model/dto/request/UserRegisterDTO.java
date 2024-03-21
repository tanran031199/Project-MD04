package com.ecommerce.model.dto.request;

import com.ecommerce.model.dto.validate.EmailExistsConstraint;
import com.ecommerce.model.dto.validate.EmailRgxConstraint;
import com.ecommerce.model.dto.validate.PasswordRgxConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @NotEmpty(message = "Trường này là bắt buộc")
    private String userName;
    @NotEmpty(message = "Trường này là bắt buộc")
    @EmailRgxConstraint
    @EmailExistsConstraint
    private String email;
    @NotEmpty(message = "Trường này là bắt buộc")
    @PasswordRgxConstraint
    private String password;
    @NotEmpty(message = "Trường này là bắt buộc")
    private String confirmPassword;
    private byte role;
}
