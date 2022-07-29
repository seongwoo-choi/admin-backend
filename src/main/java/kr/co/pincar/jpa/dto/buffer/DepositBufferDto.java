package kr.co.pincar.jpa.dto.buffer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepositBufferDto {
    private List<String> viewTexts = new ArrayList<>();
    private List<Integer> prices = new ArrayList<>();
}
