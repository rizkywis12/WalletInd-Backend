package net.mujoriwi.walletind.model.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage<T> {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
    private T errors;
}
