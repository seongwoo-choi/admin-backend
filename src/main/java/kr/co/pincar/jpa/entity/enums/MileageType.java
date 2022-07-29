package kr.co.pincar.jpa.entity.enums;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MileageType {

    T_DISTANCE("2만km"),
    TH_DISTANCE("3만km"),
    F_DISTANCE("4만km"),
    I_DISTANCE("무제한");

    private final String mileage;

    public static MileageType formValue(String value) {

        for(MileageType mileageType: values()) {
            if(mileageType.mileage.equalsIgnoreCase(value)) {
                return mileageType;
            }
        }

        throw new BaseException(BaseResponseCode.INVALID_MILEAGE);
    }
}
