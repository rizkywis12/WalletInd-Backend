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
public class DetailUserDto {
    @NotBlank(message = "first name is required")
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "input minimum 3 characters and only accepts a-z, A-Z")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone Number is required")
    @Size(min = 10, max = 15, message = "phone number must between 10 - 15 characters")
    @Pattern(regexp = "^\\d{10,15}$", message = "phone number must be number")
    private String phoneNumber;
}

