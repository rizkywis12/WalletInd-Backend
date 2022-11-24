package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class PinDto {
    @NotBlank(message = "Pin is required")
    private String pin;
}
