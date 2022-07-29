package kr.co.pincar.jpa.dto.buffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YMBufferDto {
    private List<String> viewTexts = new ArrayList<>();
    private List<Integer> prices = new ArrayList<>();
}
