package net.mujoriwi.walletind.model.entity;

import javax.persistence.Column;
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
@Table(name = "files")
@Data
@NoArgsConstructor
public class FileEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String type;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User userId;

  @Lob
  @Column(nullable = false)
  private byte[] data;

  public FileEntity(String name, String type, byte[] data) {
    this.name = name;
    this.type = type;
    this.data = data;
  }

}
