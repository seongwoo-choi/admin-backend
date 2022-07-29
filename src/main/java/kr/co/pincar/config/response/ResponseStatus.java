package kr.co.pincar.config.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),


    WRONG_PWD(2000, "잘못된 비밀번호입니다."),
    EMPTY_EMAIL(2001, "이메일을 입력하세요."),
    EMPTY_NAME(2002,"이름 또는 법인을 입력하세요."),
    EMPTY_PHONE_NUMBER(2003,"전화번호를 입력하세요."),
    INVALID_PHONE_NUMBER(2004,"올바른 형식으로 전화번호를 입력하세요."),
    INVALID_PURCHASE_TYPE(2005,"[RENT, LEASE] 중 입력하세요."),
    INVALID_CONSULT_TYPE(2006,"[KAKAO, CABLE] 중 입력하세요."),
    NON_EXISTENT_IDX(2007,"존재하지 않는 인덱스입니다."),
    EMPTY_IDX_LIST(2008,"견적문의 배분완료할 인덱스 리스트를 입력하세요."),
    INVALID_DATE(2009, "yyyy-MM-dd 형식으로 날짜를 입력하세요."),
    EMPTY_PASSWORD(2010,"비밀번호를 입력하세요."),
    INVALID_EMAIL(2011,"이메일 형식이 올바르지 않습니다."),
    NON_EXISTENT_EMAIL(2012,"존재하지 않는 이메일입니다"),
    EMPTY_ADDRESS(2013,"주소를 입력하세요."),
    EMPTY_INQUIRY_VEHICLE(2014,"문의차량을 입력하세요."),
    EMPTY_KAKAOTALK_ID(2015,"카카오톡 아이디를 입력하세요."),
    INVALID_JWT(2016,"유효하지 않는 jwt 입니다."),
    EMPTY_JWT(2017,"jwt를 입력하세요."),
    EXISTENT_EMAIL(2018, "이미 존재하는 이메일입니다."),
    EMPTY_IDX(2019,"인덱스를 입력하세요."),
    EMPTY_VEHICLE_IMAGE(2020,"차량 이미지를 입력하세요."),
    EMPTY_VEHICLE_PRICE(2021,"차량 가격을 입력하세요."),
    EMPTY_VEHICLE_NAME(2022,"차량명을 입력하세요."),
    EMPTY_PERIOD(2023,"기간을 입력하세요."),
    EMPTY_DEPOSIT(2024,"보증금을 입력하세요."),
    EMPTY_RENTAL_FEE(2025, "렌탈료를 입력하세요."),
    EMPTY_MILEAGE(2026, "주행거리를 입력하세요."),
    EMPTY_VEHICLE_TYPE(2027, "차량타입을 입력하세요."),
    INVALID_VEHICLE_TYPE(2028, "유효하지 않은 차량타입, [ECHO, SEDAN, SUV] 중 입력하세요."),
    DIVISION_IS_COMPLETE(2029, "이미 배분완료되었습니다."),
    EMPTY_VEHICLE_STATUS(2030,"차량상태를 입력하세요."),
    INVALID_VEHICLE_STATUS(2031, "유효하지 않은 차량상태, [ACTIVE, START, END, INACTIVE] 증 입력하세요."),
    EMPTY_IMAGE(2032,"이미지를 입력하세요."),
    EMPTY_TITLE(2033,"제목을 입력하세요."),
    EMPTY_LINK(2034,"링크를 입력하세요."),
    EMPTY_CONTENT(2035,"내용을 입력하세요."),
    EMPTY_DATE(2036,"날짜를 입력하세요."),
    EMPTY_SAVE_TYPE(2037,"저장타입을 입력하세요."),
    INVALID_SAVE_TYPE(2038,"유효하지 않은 저장타입, [SAVE, TEMPORARY] 중 입력하세요."),
    EMPTY_CONTENT_IMAGE(2039,"내용 이미지를 입력하세요."),
    EMPTY_THUMBNAIL(2040,"썸네일을 입력하세요."),

    FAILED_TO_SAVE_ESTIMATE(3000,"견적문의 등록에 실패했습니다."),
    FAILED_TO_FIND_ESTIMATE_LIST(3001,"견적문의 조회에 실패했습니다."),
    FAILED_TO_FIND_ESTIMATE(3002,"견적문의 상세 조회에 실패했습니다."),
    FAILED_TO_UPDATE_ESTIMATE(3003,"견적문의 수정에 실패했습니다."),
    FAILED_TO_SAVE_ADMIN(3004,"어드민 등록에 실패했습니다."),
    FAILED_TO_FIND_ADMIN_EMAIL(3005,"어드민 이메일 조회에 실패했습니다."),
    FAILED_TO_SAVE_VISIT_CONSULT(3006,"방문상담 등록에 실패했습니다."),
    FAILED_TO_FIND_VISIT_CONSULT(3007,"방문상담 조회에 실패했습니다."),
    FAILED_TO_UPDATE_VISIT_CONSULT(3008,"방문상담 수정에 실패했습니다."),
    FAILED_TO_SAVE_KAKAO_CONSULT(3009,"카카오톡 상담 등록에 실패했습니다."),
    FAILED_TO_FIND_KAKAO_CONSULT(3010,"카카오톡 상담 조회에 실패했습니다."),
    FAILED_TO_SAVE_SLIDE(3011,"슬라이드 등록에 실패했습니다."),
    FAILED_TO_FIND_ADMIN(3012,"어드민 조회에 실패했습니다."),
    FAILED_TO_FIND_SLIDE(3013,"슬라이드 조회에 실패했습니다."),
    FAILED_TO_SAVE_TEAM_PINCAR_PICK(3014,"팀핀카픽 등록에 실패했습니다."),
    FAILED_TO_FIND_TEAM_PINCAR_PICK(3015,"팀핀카픽 조회에 실패했습니다."),
    FAILED_TO_SAVE_PROMOTION(3016,"프로모션 등록에 실패했습니다."),
    FAILED_TO_FIND_PROMOTION(3017,"프로모션 조회에 실패했습니다."),
    FAILED_TO_SAVE_VEHICLE(3018,"차량 등록에 실패했습니다."),
    FAILED_TO_FIND_VEHICLE(3019,"차량 조회에 실패했습니다."),
    FAILED_TO_SAVE_RELEASE_VEHICLE(3020,"즉시 출고 차량 등록에 실패했습니다."),
    FAILED_TO_FIND_RELEASE_VEHICLE(3021,"즉시 출고 차량 조회에 실패했습니다."),
    FAILED_TO_SAVE_EVENT(3022,"이벤트 등록에 실패했습니다."),
    FAILED_TO_FIND_EVENT(3023,"이벤트 조회에 실패했습니다."),
    ;
    private final int code;
    private final String message;


}