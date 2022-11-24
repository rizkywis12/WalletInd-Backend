package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransferDto {
    private long amount;
    private String notes;
}
