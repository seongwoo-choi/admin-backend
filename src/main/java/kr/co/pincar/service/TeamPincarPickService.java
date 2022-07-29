package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductListRes;
import kr.co.pincar.jpa.dto.teamPincarPick.*;
import kr.co.pincar.jpa.entity.enums.DisplayStatus;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.jpa.entity.newEntity.Image;
import kr.co.pincar.jpa.entity.newEntity.OwnProductList;
import kr.co.pincar.jpa.entity.newEntity.TeamPincarPick;
import kr.co.pincar.jpa.entity.newEntity.TeamPincarPickImage;
import kr.co.pincar.jpa.repository.teamPincarPick.TeamPincarPickImageRepository;
import kr.co.pincar.jpa.repository.teamPincarPick.TeamPincarPickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamPincarPickService {

    private final TeamPincarPickImageRepository teamPincarPickImageRepository;
    private final TeamPincarPickRepository teamPincarPickRepository;
    private final OwnProductService ownProductService;

    // ----------------------TeamPincarPickImage---------------------------
    // 1. 팀핀카픽 차량타입별 대표이미지 등록
    public void createTeamPincarPickImage(TeamPincarPickImageReq teamPincarPickImageReq, String vehicleType) {
        Image newImage = Image.builder()
                .imageTitle("팀핀카픽 대표이미지")
                .imagePath(teamPincarPickImageReq.getImagePath())
                .build();


        TeamPincarPickImage newPickImage = TeamPincarPickImage.builder()
                .vehicleType(VehicleType.valueOf(vehicleType))
                .image(newImage)
                .build();
        teamPincarPickImageRepository.save(newPickImage);
    }

    // 2. 팀핀카픽 차량별 대표이미지 수정
    @Transactional
    public void updateTeamPincarPickImage(TeamPincarPickImageReq teamPincarPickImageReq, String vehicleType) {
        TeamPincarPickImage findPickImage = teamPincarPickImageRepository.findByVehicleType(VehicleType.valueOf(vehicleType)).orElseThrow(() -> new BaseException(BaseResponseCode.EMPTY_TEAM_PINCAR_PICK_IMAGE));

        findPickImage.getImage().changeImagePath(teamPincarPickImageReq.getImagePath());
    }



    public boolean existsByIdx(Long idx) {
        return teamPincarPickRepository.existsById(idx);
    }

    // ----------------------TeamPincarPick---------------------------
    // 0. 팀핀카픽 차량 등록 시 ownProductList 가져오기
    public List<OwnProductListRes> findAllOwnProductList(String vehicleType){
        return ownProductService.findAllOwnProductByVehicleType(vehicleType);
    }

    // 1. 팀핀카픽 차량 등록
    public void createTeamPincarPick(TeamPincarPickReq teamPincarPickReq, String vehicleType) {
        OwnProductList findOwnProduct = ownProductService.readOwnProductListById(teamPincarPickReq.getOwnProductListId());

        TeamPincarPick newPincarPick = TeamPincarPick.builder()
                .rentalFee(teamPincarPickReq.getRentalFee())
                .displayStatus(DisplayStatus.NO) // 첫 생성시에는 기본값이 노출X
                .ownProductList(findOwnProduct)
                .build();
        newPincarPick.setStatus(Status.ACTIVE);

        try {
            teamPincarPickRepository.save(newPincarPick);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_TEAM_PINCAR_PICK);
        }

    }

    // 2. 팀핀카픽 차량 삭제 -> 찐으로 삭제할껀지 INACTIVE 처리할껀지
    @Transactional
    public void deleteTeamPincarPick(List<Long> idList) {
        teamPincarPickRepository.deleteTeamPincarPick(idList);
    }

    // 3. display list 수정
    @Transactional
    public void updateTeamPincarPickDisplayList(List<Long> pickIdList) {
        // 기존에 display list로 지정되어있는 애들 모두 DisplayStatus.NO 처리
        teamPincarPickRepository.updateDisplayStatus();

        // 새롭게 display list 지정
        int index = 1;
        for (int i = 0; i < pickIdList.size(); i++) {
            TeamPincarPick findPincarPick = teamPincarPickRepository.findById(pickIdList.get(i)).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_TEAM_PINCAR_PICK));
            findPincarPick.changeDisplay(index+i, DisplayStatus.YES);
        }
    }

    // 4. 팀핀카픽 개별 수정
    @Transactional
    public void updateTeamPincarPick(Long idx, TeamPincarPickReq teamPincarPickReq) {
        TeamPincarPick findPincarPick = teamPincarPickRepository.findByIdAndStatus(idx, Status.ACTIVE).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_TEAM_PINCAR_PICK));

        OwnProductList findOwnProduct = ownProductService.readOwnProductListById(teamPincarPickReq.getOwnProductListId());

        findPincarPick.changeTeamPincarPick(teamPincarPickReq.getRentalFee(), findOwnProduct);
    }

    // 5. 팀핀카픽 개별 조회
    public TeamPincarPickRes findOneTeamPincarPick(String vehicleType, Long idx) {
        return teamPincarPickRepository.findOneTeamPincarPick(VehicleType.valueOf(vehicleType), idx);
    }

    // 6. 팀핀카픽 전체 조회(display_list + non_display_list)
    public TeamPincarPickAllListRes findAllTeamPincarPickList(String vehicleType){

        List<TeamPincarPickRes> display_list = teamPincarPickRepository.findAllTeamPincarPick(DisplayStatus.YES, VehicleType.valueOf(vehicleType));
        List<TeamPincarPickRes> no_display_list = teamPincarPickRepository.findAllTeamPincarPick(DisplayStatus.NO, VehicleType.valueOf(vehicleType));

        return new TeamPincarPickAllListRes(display_list, no_display_list);
    }


//    // 팀핀카픽 차량 수정
//    @Transactional
//    public void updateTeamPincarPick(TeamPincarPickReq teamPincarPickReq, Long idx) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String email = userDetails.getUsername();
//
//        User admin = userRepository.findByEmailAndStatus(email, "ACTIVE").orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ADMIN_EMAIL));
//        TeamPincarPick findPincarPick = teamPincarPickRepository.findById(idx).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_TEAM_PINCAR_PICK));
//
//
//        TeamPincarPick teamPincarPick = teamPincarPickRepository.findByIdxAndStatus(idx, "ACTIVE")
//                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_TEAM_PINCAR_PICK));
//
//        if(!admin.getIdx().equals(teamPincarPick.getAdmin().getIdx())) {
//            throw new BaseException(BaseResponseCode.INVALID_ADMIN);
//        }
//
//        try {
//            teamPincarPick.changeTeamPincarPick(
//                    vehicle,
//                    teamPincarPickReq.getImage(),
//                    VehicleType.valueOf(teamPincarPickReq.getVehicleType())
//            );
//            teamPincarPickRepository.save(teamPincarPick);
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_TEAM_PINCAR_PICK);
//        }
//
//    }


}
