package kr.co.pincar.jpa.entity.enums;


import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DepositType {
    NO_DEPOSIT("무보증금"),
    T_DEPOSIT("10%"),
    TW_DEPOSIT("20%"),
    TH_DEPOSIT("30%");

    private final String deposit;

    public static DepositType formValue(String value) {

        for(DepositType depositType : values()) {
            if(depositType.deposit.equalsIgnoreCase(value)) {
                return depositType;
            }
        }

        throw new BaseException(BaseResponseCode.INVALID_DEPOSIT);
    }
}
