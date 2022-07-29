package kr.co.pincar.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String content;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String writer;


    public Board(String content, String title, String writer) {
        this.content = content;
        this.title = title;
        this.writer = writer;
    }
}
