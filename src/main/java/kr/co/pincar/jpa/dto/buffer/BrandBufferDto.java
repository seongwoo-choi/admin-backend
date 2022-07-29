package kr.co.pincar.jpa.dto.buffer;

import kr.co.pincar.jpa.entity.enums.CountryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandBufferDto {

    // 브랜드 관련
    private String countryType;
    private String name;
    private String image;

}
