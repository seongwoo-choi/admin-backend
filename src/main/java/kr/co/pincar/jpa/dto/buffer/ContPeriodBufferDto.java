package kr.co.pincar.jpa.dto.buffer;

import kr.co.pincar.jpa.entity.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContPeriodBufferDto {
    private List<String> periodTypes = new ArrayList<>();
}
