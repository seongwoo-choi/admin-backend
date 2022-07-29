package kr.co.pincar.jpa.dto.teamPincarPick;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamPincarPickAndOwnProductRes {
    private Long pickId;
    private Long ownProductId;

    @QueryProjection
    public TeamPincarPickAndOwnProductRes(Long pickId, Long ownProductId) {
        this.pickId = pickId;
        this.ownProductId = ownProductId;
    }
}
