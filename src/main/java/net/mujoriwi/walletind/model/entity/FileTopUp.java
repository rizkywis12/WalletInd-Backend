package net.mujoriwi.walletind.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files_top_up")
@Data
@NoArgsConstructor
public class FileTopUp {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;
    @OneToOne
    @JoinColumn(name = "topUp_id")
    private TopUp topUpId;

    @Lob
    private byte[] data;

    public FileTopUp(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
