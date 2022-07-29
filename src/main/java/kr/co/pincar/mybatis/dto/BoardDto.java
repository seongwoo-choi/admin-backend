package kr.co.pincar.mybatis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardDto {

    private Long id;

    private String content;
    private String title;
    private String writer;

    private String createdAt;
    private String updatedAt;
}
