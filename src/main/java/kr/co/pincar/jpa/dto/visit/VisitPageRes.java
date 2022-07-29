package kr.co.pincar.jpa.dto.visit;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class VisitPageRes {
    @ApiModelProperty(value = "전체 페이지 수", example = "10", required = true)
    private int totalPage;
    @ApiModelProperty(value = "조회 리스트", required = true)
    private List<VisitRes> consultList;
}
