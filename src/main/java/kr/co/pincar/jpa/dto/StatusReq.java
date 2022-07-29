package kr.co.pincar.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusReq {
    @ApiModelProperty(value = "아이디 리스트", example = "1, 2, 3", required = true)
    private List<Long> id = new ArrayList<>();

    @ApiModelProperty(value = "게시 상태 여부", example = "true", required = true)
    private String status;
}
