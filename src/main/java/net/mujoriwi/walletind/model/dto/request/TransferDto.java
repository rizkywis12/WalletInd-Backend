package net.mujoriwi.walletind.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransferDto {

    @NotNull(message = "Amount is required")
    private Long amount;

    @NotBlank(message = "Notes is required")
    private String notes;

    private String pin;

    private String status;
}
