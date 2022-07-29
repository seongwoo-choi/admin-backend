package kr.co.pincar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.IdListReq;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductListRes;
import kr.co.pincar.jpa.dto.teamPincarPick.*;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.service.OwnProductService;
import kr.co.pincar.service.TeamPincarPickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@Slf4j
@Api(tags = {"팀핀카픽"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamPincarPickController {
    private final TeamPincarPickService teamPincarPickService;
    private final OwnProductService ownProductService;

    // ----------------------------TeamPincarPickImage---------------------
    // 1. 팀핀카픽 차량별 대표이미지 등록
    @PostMapping("/team-pincar-pick-images/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 차량별 대표이미지 등록 API", notes = "팀핀카픽 차량별 대표이미지를 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPincarPickImageReq", value = "팀핀카픽 차량별 대표이미지 Request DTO", required = true, dataType = "TeamPincarPickImageReq"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> createTeamPincarPickImage(@RequestBody @Valid TeamPincarPickImageReq teamPincarPickImageReq, @PathVariable String vehicleType) {

        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        teamPincarPickService.createTeamPincarPickImage(teamPincarPickImageReq, vehicleType);

        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 2. 팀핀카픽 차량별 대표이미지 수정
    @PatchMapping("/team-pincar-pick-images/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 차량별 대표이미지 수정 API", notes = "팀핀카픽 차량별 대표이미지를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPincarPickImageReq", value = "팀핀카픽 차량별 대표이미지 Request DTO", required = true, dataType = "TeamPincarPickImageReq"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> updateTeamPincarPickImage(@RequestBody @Valid TeamPincarPickImageReq teamPincarPickImageReq, @PathVariable String vehicleType) {
        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        teamPincarPickService.updateTeamPincarPickImage(teamPincarPickImageReq, vehicleType);
        return new BaseResponse<>(BaseResponseCode.OK);
    }




    // -------------------------TeamPincarPick------------------------------

    // 0. 팀핀카픽 등록 시 ownProduct 리스트 가져오기
    @GetMapping("/team-pincar-picks/{vehicleType}/new")
    @ApiOperation(value = "팀핀카픽 차량 등록 시 보유차량 조회 API", notes = "팀핀카픽 차량 등록 시 보유차량 리스트를 가져옵니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<List<OwnProductListRes>> findAllOwnProductListByVehicleType(@PathVariable String vehicleType) {
        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        List<OwnProductListRes> ownProductList = teamPincarPickService.findAllOwnProductList(vehicleType);
        return new BaseResponse<>(ownProductList);
    }

    // 1. 팀핀카픽 등록
    @PostMapping("/team-pincar-picks/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 차량 등록 API", notes = "팀핀카픽 차량을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPincarPickReq", value = "팀핀카픽 Request DTO", required = true, dataType = "TeamPincarPickReq"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> createTeamPincarPick(@RequestBody @Valid TeamPincarPickReq teamPincarPickReq, @PathVariable String vehicleType) {

        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        boolean isVehicle = ownProductService.existsById(teamPincarPickReq.getOwnProductListId());
        if (!isVehicle) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        teamPincarPickService.createTeamPincarPick(teamPincarPickReq, vehicleType);
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 2. 팀핀카픽 삭제
    @DeleteMapping("/team-pincar-picks/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 차량 삭제 API", notes = "팀핀카픽 차량을 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idListReq", value = "id 리스트  DTO", required = true),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> deleteTeamPincarPick(@RequestBody IdListReq idListReq, @PathVariable String vehicleType) {
        // idx값을 확인해주는 쿼리가 필요한지 의문이긴함
        for (Long idx : idListReq.getIdList()) {
            boolean isEvent = teamPincarPickService.existsByIdx(idx);
            if (!isEvent) {
                return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
            }
        }

        teamPincarPickService.deleteTeamPincarPick(idListReq.getIdList());

        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 3. 팀핀카픽 display List 수정 [ 저장버튼 ]
    @PatchMapping("/team-pincar-picks/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 화면노출 리스트 수정 API", notes = "팀핀카픽 화면노출 리스트를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPincarPickDisplayReq", value = "팀핀카픽 Request DTO", required = true, dataType = "TeamPincarPickDisplayReq"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> updateTeamPincarPickDisplayList(@RequestBody @Valid TeamPincarPickDisplayReq teamPincarPickDisplayReq,
                                                  @PathVariable String vehicleType) {
        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }
        teamPincarPickService.updateTeamPincarPickDisplayList(teamPincarPickDisplayReq.getPickIdList());
        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 4. 팀핀카픽 개별 수정
    @PatchMapping("/team-pincar-picks/{vehicleType}/{idx}")
    @ApiOperation(value = "팀핀카픽 수정 API", notes = "팀핀카픽를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamPincarPickReq", value = "팀핀카픽 Request DTO", required = true, dataType = "TeamPincarPickReq"),
            @ApiImplicitParam(name = "idx", value = "팀핀카픽 id", dataType = "Long", required = true, paramType = "path"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<Void> updateTeamPincarPick(@RequestBody @Valid TeamPincarPickReq teamPincarPickReq,
            @PathVariable Long idx, @PathVariable String vehicleType) {
        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        boolean isEvent = teamPincarPickService.existsByIdx(idx);
        if (!isEvent) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        teamPincarPickService.updateTeamPincarPick(idx, teamPincarPickReq);
        return new BaseResponse<>(BaseResponseCode.OK);
    }


    // 5. 팀핀카픽 개별 조회(수정용)
    @GetMapping("/team-pincar-picks/{vehicleType}/{idx}")
    @ApiOperation(value = "팀핀카픽 단건 조회 API", notes = "팀핀카픽를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "팀핀카픽 id", dataType = "Long", required = true, paramType = "path"),
            @ApiImplicitParam(name = "vehicleType", value = "차량 타입", dataType = "String", required = true, paramType = "path")
    })
    public BaseResponse<TeamPincarPickRes> findOneTeamPincarPick(@PathVariable String vehicleType, @PathVariable Long idx) {
        TeamPincarPickRes result = teamPincarPickService.findOneTeamPincarPick(vehicleType, idx);
        return new BaseResponse<>(result);
    }




    // 팀핀카픽 차량 타입별 전체 조회
    @GetMapping("/team-pincar-picks/{vehicleType}")
    @ApiOperation(value = "팀핀카픽 차량 조회 API", notes = "팀핀카픽 차량 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "팀핀카픽 타입", dataType = "String", required = true, paramType = "query")
    })
    public BaseResponse<TeamPincarPickAllListRes> getTeamPincarPick(@PathVariable String vehicleType) {
        if (!EnumUtils.isValidEnum(VehicleType.class, vehicleType)) {
            return new BaseResponse<>(BaseResponseCode.INVALID_VEHICLE_TYPE);
        }

        TeamPincarPickAllListRes allTeamPincarPickList = teamPincarPickService.findAllTeamPincarPickList(vehicleType);
        return new BaseResponse<>(allTeamPincarPickList);
    }


}
