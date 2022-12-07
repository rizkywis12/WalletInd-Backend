package net.mujoriwi.walletind.model.entity;

import lombok.Data;
import net.mujoriwi.walletind.model.entity.Role;
import javax.persistence.*;

@Entity
@Data
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
}

