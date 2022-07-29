package kr.co.pincar.jpa.dto.buffer;

import kr.co.pincar.jpa.entity.enums.PurchaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBufferDto {

    private String purchaseType;
    private String name;
    private String image;
    private int price;
}
