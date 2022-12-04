package net.mujoriwi.walletind.model.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TopUpTransactionDto {
    @NotNull(message = "Amount is required")
    private Long amount;

    private String status;
}
