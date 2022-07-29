package kr.co.pincar.jpa.repository.teamPincarPick;

import kr.co.pincar.jpa.dto.teamPincarPick.TeamPincarPickAndOwnProductRes;
import kr.co.pincar.jpa.dto.teamPincarPick.TeamPincarPickRes;
import kr.co.pincar.jpa.entity.enums.DisplayStatus;
import kr.co.pincar.jpa.entity.enums.VehicleType;

import java.util.List;

public interface TeamPincarPickRepositoryCustom {
    long deleteTeamPincarPick(List<Long> idList);

    long updateDisplayStatus();

    TeamPincarPickRes findOneTeamPincarPick(VehicleType vehicleType, Long pick_id);

    List<TeamPincarPickRes> findAllTeamPincarPick(DisplayStatus displayStatus, VehicleType vehicleType);
}
