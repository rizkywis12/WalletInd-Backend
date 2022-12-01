package net.mujoriwi.walletind.model.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "top_up")
@Data
@NoArgsConstructor
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String paymentName;

    public TopUp(String paymentName) {
        this.paymentName = paymentName;
    }

}