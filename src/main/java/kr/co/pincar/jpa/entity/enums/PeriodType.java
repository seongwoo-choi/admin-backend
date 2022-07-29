package kr.co.pincar.jpa.entity.enums;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PeriodType {
    TF_MONTH("24개월"),
    TS_MONTH("36개월"),
    FE_MONTH("48개월"),
    S_MONTH("60개월");

    private final String period;

    public static PeriodType formValue(String value) {

        for(PeriodType periodType : values()) {
            if(periodType.period.equalsIgnoreCase(value)) {
                return periodType;
            }
        }
        throw new BaseException(BaseResponseCode.INVALID_PERIOD);
    }
}
