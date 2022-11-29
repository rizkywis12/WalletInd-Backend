package net.mujoriwi.walletind.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TopupDto {
        @NotNull(message = "Amount is required")
        private Long amount;
}
