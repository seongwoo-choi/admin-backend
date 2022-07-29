package kr.co.pincar.exeception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),


    OK(HttpStatus.OK, "요청 성공하였습니다."),

    WRONG_PWD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "이메일을 입력하세요."),
    EMPTY_NAME(HttpStatus.BAD_REQUEST, "이름 또는 법인을 입력하세요."),
    EMPTY_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "전화번호를 입력하세요."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "올바른 형식으로 전화번호를 입력하세요."),
    INVALID_PURCHASE_TYPE(HttpStatus.BAD_REQUEST, "[RENT, LEASE] 중 입력하세요."),
    INVALID_CONSULT_TYPE(HttpStatus.BAD_REQUEST, "[KAKAO, CABLE] 중 입력하세요."),
    INVALID_DIVISION_TYPE(HttpStatus.BAD_REQUEST, "true, false 중 입력하세요."),
    INVALID_DIVISION_STATUS(HttpStatus.BAD_REQUEST, "상태가 COMPLETE 인 배분만 판매 등록이 가능합니다."),
    INVALID_DIVISION_TYPE_FALSE(HttpStatus.BAD_REQUEST, "잘못된 입력입니다. [true] 를 입력하세요."),
    INVALID_DIVISION_TYPE_TRUE(HttpStatus.BAD_REQUEST, "잘못된 입력입니다. [false] 를 입력하세요."),
    NON_EXISTENT_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 인덱스입니다."),
    EMPTY_IDX_LIST(HttpStatus.BAD_REQUEST, "견적문의 배분완료할 인덱스 리스트를 입력하세요."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "yyyy-MM-dd 형식으로 날짜를 입력하세요."),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 입력하세요."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    NON_EXISTENT_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다"),
    EMPTY_ADDRESS(HttpStatus.BAD_REQUEST, "주소를 입력하세요."),
    EMPTY_INQUIRY_VEHICLE(HttpStatus.BAD_REQUEST, "문의차량을 입력하세요."),
    EMPTY_KAKAOTALK_ID(HttpStatus.BAD_REQUEST, "카카오톡 아이디를 입력하세요."),
    INVALID_JWT(HttpStatus.BAD_REQUEST, "유효하지 않는 jwt 입니다."),
    EMPTY_JWT(HttpStatus.BAD_REQUEST, "jwt를 입력하세요."),
    EXISTENT_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    EMPTY_IDX(HttpStatus.BAD_REQUEST, "인덱스를 입력하세요."),
    EMPTY_VEHICLE_IMAGE(HttpStatus.BAD_REQUEST, "차량 이미지를 입력하세요."),
    EMPTY_VEHICLE_PRICE(HttpStatus.BAD_REQUEST, "차량 가격을 입력하세요."),
    EMPTY_VEHICLE_NAME(HttpStatus.BAD_REQUEST, "차량명을 입력하세요."),
    EMPTY_PERIOD(HttpStatus.BAD_REQUEST, "기간을 입력하세요."),
    INVALID_PERIOD(HttpStatus.BAD_REQUEST, "유효한 기간을 입력하세요."),
    EMPTY_DEPOSIT(HttpStatus.BAD_REQUEST, "보증금을 입력하세요."),
    INVALID_DEPOSIT(HttpStatus.BAD_REQUEST, "유효한 보증금을 입력하세요."),
    EMPTY_RENTAL_FEE(HttpStatus.BAD_REQUEST, "렌탈료를 입력하세요."),
    EMPTY_MILEAGE(HttpStatus.BAD_REQUEST, "주행거리를 입력하세요."),
    INVALID_MILEAGE(HttpStatus.BAD_REQUEST, "유효한 주행거리를 입력하세요."),
    EMPTY_VEHICLE_TYPE(HttpStatus.BAD_REQUEST, "차량타입을 입력하세요."),
    INVALID_VEHICLE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 차량타입, [ECHO, SEDAN, SUV] 중 입력하세요."),
    DIVISION_IS_COMPLETE(HttpStatus.BAD_REQUEST, "이미 배분완료되었습니다."),
    EMPTY_VEHICLE_STATUS(HttpStatus.BAD_REQUEST, "차량상태를 입력하세요."),
    INVALID_VEHICLE_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 차량상태, [ACTIVE, START, END, INACTIVE] 증 입력하세요."),
    EMPTY_IMAGE(HttpStatus.BAD_REQUEST, "이미지를 입력하세요."),
    EMPTY_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력하세요."),
    EMPTY_LINK(HttpStatus.BAD_REQUEST, "링크를 입력하세요."),
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력하세요."),
    EMPTY_DATE(HttpStatus.BAD_REQUEST, "날짜를 입력하세요."),
    EMPTY_SAVE_TYPE(HttpStatus.BAD_REQUEST, "저장타입을 입력하세요."),
    INVALID_SAVE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 저장타입 입니다."),
    EMPTY_CONTENT_IMAGE(HttpStatus.BAD_REQUEST, "내용 이미지를 입력하세요."),
    EMPTY_THUMBNAIL(HttpStatus.BAD_REQUEST, "썸네일을 입력하세요."),
    BOTH_DATE(HttpStatus.BAD_REQUEST, "시작일자, 종료일자 모두 입력하세요."),
    EMPTY_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "path variable을 입력하세요."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "유효하지 않은 페이지입니다. 1부터 입력하세요."),
    INVALID_ADMIN(HttpStatus.BAD_REQUEST, "유효하지 않은 어드민입니다."),
    INVALID_ENUM_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 타입의 값입니다."),
    INVALID_STAFFID(HttpStatus.BAD_REQUEST, "유효하지 않은 스태프 아이디입니다."),
    INVALID_PRODUCT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 상품타입"),
    EMPTY_TEAM_PINCAR_PICK_IMAGE(HttpStatus.BAD_REQUEST, "해당 차량타입에 이미지가 등록되지 않았습니다. 이미지를 등록해주세요."), // teampincarpickImage 추가
    INVALID_EVENT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 이벤트 상태값, [PROCEEDING, STAND_BY] 중 입력하세요."), // event 추가
    INVALID_USER(HttpStatus.BAD_REQUEST, "유효하지 않은 유저입니다."),
    INVALID_MAIN_PRODUCT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 메인 상품 타입,[SUV, SEDAN, ECO, INSTANT, PROMOTION] 중 입력하세요."),



    FAILED_TO_SAVE_ESTIMATE(HttpStatus.NOT_FOUND, "견적문의 등록에 실패했습니다."),
    FAILED_TO_FIND_ESTIMATE_LIST(HttpStatus.NOT_FOUND, "견적문의 조회에 실패했습니다."),
    FAILED_TO_FIND_ESTIMATE(HttpStatus.NOT_FOUND, "견적문의 상세 조회에 실패했습니다."),
    FAILED_TO_UPDATE_ESTIMATE(HttpStatus.NOT_FOUND, "견적문의 수정에 실패했습니다."),
    FAILED_TO_SAVE_ADMIN(HttpStatus.NOT_FOUND, "어드민 등록에 실패했습니다."),
    FAILED_TO_FIND_ADMIN_EMAIL(HttpStatus.NOT_FOUND, "어드민 이메일 조회에 실패했습니다."),
    FAILED_TO_SAVE_VISIT_CONSULT(HttpStatus.NOT_FOUND, "방문상담 등록에 실패했습니다."),
    FAILED_TO_SAVE_DIVISION(HttpStatus.NOT_FOUND, "배분 등록에 실패했습니다."),
    FAILED_TO_SAVE_DIVISION_STATUS(HttpStatus.NOT_FOUND, "배분 상태 변경에 실패했습니다. 배분 완료된 문의만 선택하세요."),
    FAILED_TO_DELETE_DIVISION(HttpStatus.NOT_FOUND, "배분 삭제에 실패했습니다."),
    FAILED_TO_SAVE_DIVISION_HISTORY(HttpStatus.NOT_FOUND, "배분 히스토리 등록에 실패했습니다."),
    FAILED_TO_FIND_DIVISION(HttpStatus.NOT_FOUND, "배분 조회에 실패했습니다."),
    FAILED_TO_FIND_SALE(HttpStatus.NOT_FOUND, "판매 조회에 실패했습니다."),
    FAILED_TO_FIND_DIVISION_STATUS(HttpStatus.NOT_FOUND, "배분 상태 조회에 실패했습니다."),
    FAILED_TO_FIND_VISIT_CONSULT(HttpStatus.NOT_FOUND, "방문상담 조회에 실패했습니다."),
    FAILED_TO_UPDATE_VISIT_CONSULT(HttpStatus.NOT_FOUND, "방문상담 수정에 실패했습니다."),
    FAILED_TO_SAVE_KAKAO_CONSULT(HttpStatus.NOT_FOUND, "카카오톡 상담 등록에 실패했습니다."),
    FAILED_TO_FIND_KAKAO_CONSULT(HttpStatus.NOT_FOUND, "카카오톡 상담 조회에 실패했습니다."),
    FAILED_TO_FIND_CONSULT(HttpStatus.NOT_FOUND, "문의 조회에 실패했습니다."),
    FAILED_TO_SAVE_SLIDE(HttpStatus.NOT_FOUND, "슬라이드 등록에 실패했습니다."),
    FAILED_TO_FIND_ADMIN(HttpStatus.NOT_FOUND, "어드민 조회에 실패했습니다."),
    FAILED_TO_FIND_SLIDE(HttpStatus.NOT_FOUND, "슬라이드 조회에 실패했습니다."),
    FAILED_TO_SAVE_TEAM_PINCAR_PICK(HttpStatus.NOT_FOUND, "팀핀카픽 등록에 실패했습니다."),
    FAILED_TO_FIND_TEAM_PINCAR_PICK(HttpStatus.NOT_FOUND, "팀핀카픽 조회에 실패했습니다."),
    FAILED_TO_SAVE_PROMOTION(HttpStatus.NOT_FOUND, "프로모션 등록에 실패했습니다."),
    FAILED_TO_FIND_PROMOTION(HttpStatus.NOT_FOUND, "프로모션 조회에 실패했습니다."),
    FAILED_TO_SAVE_VEHICLE(HttpStatus.NOT_FOUND, "차량 등록에 실패했습니다."),
    FAILED_TO_FIND_VEHICLE(HttpStatus.NOT_FOUND, "차량 조회에 실패했습니다."),
    FAILED_TO_SAVE_RELEASE_VEHICLE(HttpStatus.NOT_FOUND, "즉시 출고 차량 등록에 실패했습니다."),
    FAILED_TO_FIND_RELEASE_VEHICLE(HttpStatus.NOT_FOUND, "즉시 출고 차량 조회에 실패했습니다."),
    FAILED_TO_SAVE_EVENT(HttpStatus.NOT_FOUND, "이벤트 등록에 실패했습니다."),
    FAILED_TO_FIND_EVENT(HttpStatus.NOT_FOUND, "이벤트 조회에 실패했습니다."),
    FAILED_TO_FIND_PRODUCT(HttpStatus.NOT_FOUND, "상품 조회에 실패했습니다."),
    FAILED_TO_SAVE_PRODUCT(HttpStatus.NOT_FOUND, "상품 등록에 실패했습니다."),
    FAILED_TO_FIND_MAIN_PRODUCT_LIST(HttpStatus.NOT_FOUND, "상품 리스트 조회에 실패했습니다."),
    FAILED_TO_SAVE_MAIN_PRODUCT_LIST(HttpStatus.NOT_FOUND, "상품 리스트 등록에 실패했습니다."),
    FAILED_TO_FIND_BRAND(HttpStatus.NOT_FOUND, "브랜드 리스트 조회에 실패했습니다."),
    FAILED_TO_FIND_CONTRACT_PERIOD(HttpStatus.NOT_FOUND, "계약 기간 조회에 실패했습니다."),
    FAILED_TO_FIND_PREPAYMENT(HttpStatus.NOT_FOUND, "선납금 조회에 실패했습니다."),
    FAILED_TO_FIND_DEPOSIT(HttpStatus.NOT_FOUND, "보증금 조회에 실패했습니다."),
    FAILED_TO_FIND_YEAR_MILEAGE(HttpStatus.NOT_FOUND, "연간주행거리 조회에 실패했습니다."),
    FAILED_TO_SAVE_OWN_PRODUCT_LIST(HttpStatus.NOT_FOUND, "보유 상품 리스트 등록에 실패했습니다."),
    FAILED_TO_SAVE_PROMOTION_PRODUCT_LIST(HttpStatus.NOT_FOUND, "프로모션 상품 리스트 등록에 실패했습니다."),
    FAILED_TO_SAVE_SALE(HttpStatus.NOT_FOUND, "판매 등록에 실패했습니다."),
    FAILED_TO_FIND_OWN_PRODUCT_LIST(HttpStatus.NOT_FOUND, "보유 상품 리스트 조회에 실패했습니다."),
    FAILED_TO_SAVE_INSTANT_RELEASE(HttpStatus.NOT_FOUND, "즉시 출고 등록에 실패했습니다."),
    FAILED_TO_FIND_INSTANT_RELEASE(HttpStatus.NOT_FOUND, "즉시 출고 조회에 실패했습니다."),
    FAILED_TO_FIND_TEAM_PINCAR_PICK_IMAGE(HttpStatus.NOT_FOUND, "팀핀카픽 조회에 실패했습니다."), // teamPincarPickImage 추가
    FAILED_TO_FIND_PROMOTION_PRODUCT_LIST(HttpStatus.NOT_FOUND, "프로모션 상품 리스트 조회에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String message;

}