package kr.co.pincar.jpa.dto.buffer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorBufferDto {
    private List<String> names = new ArrayList<>();
    private List<String> types = new ArrayList<>();
    private List<Integer> prices = new ArrayList<>();
}
