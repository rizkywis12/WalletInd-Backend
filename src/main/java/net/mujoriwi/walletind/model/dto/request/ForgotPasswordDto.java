package net.mujoriwi.walletind.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ForgotPasswordDto {
    @Pattern(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", message = "Please input correct email format! Ex: example@domain.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters")
    private String password;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters")
    private String confirmPassword;
}
