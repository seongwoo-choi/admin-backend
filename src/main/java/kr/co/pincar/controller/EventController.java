package kr.co.pincar.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.pincar.exeception.BaseResponse;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.IdListReq;
import kr.co.pincar.jpa.dto.event.*;
import kr.co.pincar.jpa.entity.enums.EventStatus;
import kr.co.pincar.jpa.entity.enums.SaveType;
import kr.co.pincar.service.EventService;
import kr.co.pincar.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Api(tags = {"이벤트"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    // 5. 이벤트 추첨정보 입력(EventWinner생성)
    // 6. 이벤트 추첨정보 수정
    // 7. 이벤트 추첨정보 삭제? -> 이게 과연 필요쓰? 필요할지도ㅎ(EventWinner의 연관관계만 삭제한다)


    // 1. 이벤트 생성
    @PostMapping("/events")
    @ApiOperation(value = "이벤트 저장 API", notes = "이벤트를 저장합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventReq", value = "이벤트 Request DTO", required = true, dataType = "EventReq"),
    })
    public BaseResponse<Void> createEvent(@RequestBody @Valid EventReq eventReq) {

        // saveType 확인
        if (!EnumUtils.isValidEnum(SaveType.class, eventReq.getSaveType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_ENUM_TYPE);
        }

        // 이벤트 생성 시 이벤트 상태값은 PROCEEDING, STAND_BY만 가능
        if (!EnumUtils.isValidEnum(EventStatus.class, eventReq.getEventStatus())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_EVENT_STATUS);
        }

//        EventStatus inputEventStatus = EventStatus.valueOf(eventReq.getEventStatus());
//        if (inputEventStatus != EventStatus.PROCEEDING || inputEventStatus != EventStatus.STAND_BY) {
//            return new BaseResponse<>(BaseResponseCode.INVALID_EVENT_STATUS);
//        }

        eventService.createEvent(eventReq);

        return new BaseResponse<>(BaseResponseCode.OK);

    }

    // 2. 이벤트 수정
    @PatchMapping("/events/{idx}")
    @ApiOperation(value = "이벤트 수정 API", notes = "이벤트를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "이벤트 id", dataType = "Long", required = true, paramType = "path"),
            @ApiImplicitParam(name = "eventReq", value = "이벤트 Request DTO", required = true, dataType = "EventReq")
    })
    public BaseResponse<Void> updateEvent(@PathVariable Long id, @RequestBody @Valid EventReq eventReq) {

        // 인덱스값 확인
        boolean isEvent = eventService.existsByIdx(id);
        if (!isEvent) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        // saveType 확인
        if (!EnumUtils.isValidEnum(SaveType.class, eventReq.getSaveType())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_ENUM_TYPE);
        }

        // 들어온 EventStatus, EventType 확인
        if (!EnumUtils.isValidEnum(EventStatus.class, eventReq.getEventStatus())) {
            return new BaseResponse<>(BaseResponseCode.INVALID_ENUM_TYPE);
        }

        eventService.updateEvent(eventReq, id);

        return new BaseResponse<>(BaseResponseCode.OK);

    }

    // 3. 이벤트 상태값 수정(종료처리)
    @PostMapping("/events/end")
    @ApiOperation(value = "이벤트 상태변경 API", notes = "이벤트 상태값을 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idListReq", value = "id 리스트  DTO", required = true)
    })
    public BaseResponse<Void> changeEventStatus(@RequestBody IdListReq idListReq){
        // idx값을 확인해주는 쿼리가 필요한지 의문이긴함
        for (Long id : idListReq.getIdList()) {
            boolean isEvent = eventService.existsByIdx(id);
            if (!isEvent) {
                return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
            }
        }

        eventService.changeEventStatus(idListReq);

        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 4. 이벤트 삭제
    @DeleteMapping("/events")
    @ApiOperation(value = "이벤트 삭제 API", notes = "이벤트 상태값을 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idListReq", value = "id 리스트  DTO", required = true)
    })
    public BaseResponse<Void> deleteEvents(@RequestBody IdListReq idListReq) {
        // idx값을 확인해주는 쿼리가 필요한지 의문이긴함
        for (Long id : idListReq.getIdList()) {
            boolean isEvent = eventService.existsByIdx(id);
            if (!isEvent) {
                return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
            }
        }

        eventService.deleteEvents(idListReq);

        return new BaseResponse<>(BaseResponseCode.OK);
    }

    // 5. 이벤트 단건 조회(수정용)
    @GetMapping("/events/{idx}")
    @ApiOperation(value = "이벤트 상세조회 API", notes = "이벤트를 상세조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "이벤트 id", dataType = "Long", required = true, paramType = "path")
    })
    public BaseResponse<EventRes> getEvent(@PathVariable Long id) {
        boolean isEvent = eventService.existsByIdx(id);
        if (!isEvent) {
            return new BaseResponse<>(BaseResponseCode.NON_EXISTENT_ID);
        }

        EventRes eventRes = eventService.readEvent(id);
        return new BaseResponse<>(eventRes);
    }

    // 6. 이벤트 리스트 조회
    @GetMapping("/events")
    @ApiOperation(value = "이벤트 조회 API", notes = "이벤트 리스트를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "시작일자", dataType = "LocalDate"),
            @ApiImplicitParam(name = "endDate", value = "종료일자", dataType = "LocalDate")
    })
    public BaseResponse<EventPageRes> getEvents(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @PageableDefault(size = 10, sort = "startAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 시작일자를 기준으로 조회한다.
        return new BaseResponse<>(eventService.readEventsList(startDate, endDate, pageable));
    }



}
