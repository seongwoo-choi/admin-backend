package kr.co.pincar.jpa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdListReq {

    @ApiModelProperty(value = "이벤트 id 리스트", example = "[1, 2, 3]", required = true)
    private List<Long> idList = new ArrayList<>();

}
