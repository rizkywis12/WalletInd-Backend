package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class PinDto {
    @NotBlank(message = "Pin is required")
    @Pattern(regexp = "^\\d{6}$", message = "Pin Must 6 Digit")
    private String pin;
}
