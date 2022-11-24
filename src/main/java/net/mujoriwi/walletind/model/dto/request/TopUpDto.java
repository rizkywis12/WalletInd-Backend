package net.mujoriwi.walletind.model.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopUpDto {

    @NotBlank(message = "Payment name is required")
    private String paymentName;
}
