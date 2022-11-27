package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ChangePasswordDto {
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters")
    private String currentPassword;

    @NotBlank(message = "New Password is required")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters")
    private String newPassword;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters")
    private String newConfirmPassword;
}
