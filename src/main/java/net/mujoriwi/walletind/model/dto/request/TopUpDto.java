package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopUpDto {

    @NotBlank(message = "Payment name is required")
    private String paymentName;
}