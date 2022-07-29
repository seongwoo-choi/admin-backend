package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionDeleteReq;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.division.DivisionStatusUpdateReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.dto.kakao.*;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.newEntity.Division;
import kr.co.pincar.jpa.entity.newEntity.DivisionHistory;
import kr.co.pincar.jpa.entity.newEntity.Kakao;
import kr.co.pincar.jpa.repository.KakaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static kr.co.pincar.utils.ValidationRegex.isStringEmpty;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {
    private final KakaoRepository kakaoRepository;
    private final DivisionService divisionService;

    public Boolean existsById(Long id) {
        return kakaoRepository.existsById(id);
    }

    public Kakao findKakao(Long id) {
        return kakaoRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT));
    }

    // 카카오톡 상담 등록
    @Transactional
    public void createKakaos(KakaoCreateReq req) {
        Kakao kakao;
        try {
            kakao = Kakao.builder()
                    .kakao_id(req.getKakaoId())
                    .car_name(req.getCarName())
                    .build();

            kakaoRepository.save(kakao);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_KAKAO_CONSULT);
        }
    }

    // 카카오톡 상담 수정
    @Transactional
    public void updateKakaos(KakaoUpdateReq req, Long id) {

        Kakao kakao = kakaoRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT));

        try {
            kakao.changeKakaoConsult(req.getKakaoId(), req.getCarName());
            kakaoRepository.save(kakao);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_KAKAO_CONSULT);
        }
    }

    // 카카오톡 상담 조회
    @Transactional
    public KakaoPageRes readKakaosList(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Kakao> kakaoList;
        LocalDateTime startDatetime = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        if (pageable.getPageNumber() < 1) {
            throw new BaseException(BaseResponseCode.INVALID_PAGE);
        }

        try {
            kakaoList = kakaoRepository.findByCreatedAtBetween(startDatetime, endDatetime, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT);
        }

        List<KakaoRes> kakaoResList = new ArrayList<>();

        kakaoList.getContent().forEach((item) -> {

            String division_complete;
            String division_at;
            if (item.getDivision_complete()) {
                division_complete = "완료";
                division_at = item.getDivision_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                division_complete = null;
                division_at = "";
            }

            KakaoRes kakaoRes = KakaoRes.builder()
                    .id(item.getId())
                    .createdAt(item.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .kakaoId(item.getKakao_id())
                    .carName(item.getCar_name())
                    .divisionComplete(division_complete)
                    .divisionAt(division_at)
                    .build();
            kakaoResList.add(kakaoRes);
        });


        return KakaoPageRes.builder()
                .totalPage(kakaoList.getTotalPages())
                .consultList(kakaoResList)
                .build();
    }

    // 카카오톡 상담 상세조회
    @Transactional
    public KakaoRes readKakaos(Long id) {
        Kakao kakao = kakaoRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT));

        String division_complete;
        String division_at;
        if (kakao.getDivision_complete()) {
            division_complete = "완료";
            division_at = kakao.getDivision_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            division_complete = null;
            division_at = "";
        }

        return KakaoRes.builder()
                .id(kakao.getId())
                .carName(kakao.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .kakaoId(kakao.getKakao_id())
                .carName(kakao.getCar_name())
                .divisionComplete(division_complete)
                .divisionAt(division_at)
                .build();
    }

    // division 변경을 하면 division 만 변경해준다.
    @Transactional
    public void changeKakaoDivision(DivisionStatusUpdateReq req) {
        Kakao kakao = findKakao(req.getId());

        DivisionStatus status;

        if (isStringEmpty(req.getDivisionStatus())) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        } else if (req.getDivisionStatus().equals("INPROGRESS")) {
            kakao.getDivision().setDivision_status(DivisionStatus.INPROGRESS);
            kakao.getDivision().setComplete_at(null);
        } else if (req.getDivisionStatus().equals("COMPLETE")) {
            kakao.getDivision().setDivision_status(DivisionStatus.COMPLETE);
            kakao.getDivision().setComplete_at(LocalDate.now());
        } else if (req.getDivisionStatus().equals("CANCEL")) {
            kakao.getDivision().setDivision_status(DivisionStatus.CANCEL);
            kakao.getDivision().setComplete_at(null);
            kakao.setDivision_at(null);
            kakao.getDivision().setStaff_id(null);
        } else {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        }


        saveDivisionHistory(kakao.getDivision(), req.getReason());

        try {
            kakaoRepository.save(kakao);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_KAKAO_CONSULT);
        }

    }

    // staffId 를 변경 하면 division_history 로 이동, division_status ready 로 초기화
    @Transactional
    public void changeStaffId(Long id, DivisionHistoryCreateReq req) {

        if (isStringEmpty(req.getStaffId())) {
            throw new BaseException(BaseResponseCode.INVALID_STAFFID);
        }

        Kakao kakao = findKakao(id);
        Division division = divisionService.changeStaffId(kakao.getDivision().getId(), req);
        saveDivisionHistory(division, req.getReason());

    }

    // 카카오톡 상담 배분완료
    @Transactional
    public void createKakaosDivision(DivisionReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Kakao kakao = kakaoRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT));
            if (kakao.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                kakao.changeDivision(true);
                Division division = Division.builder()
                        .staff_id(req.getStaffId())
                        .division_status(DivisionStatus.INPROGRESS)
                        .question(kakao)
                        .build();

                kakao.setDivision(divisionService.saveDivision(division));
                kakaoRepository.save(kakao);

                saveDivisionHistory(kakao.getDivision(), null);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_KAKAO_CONSULT);
            }
        }
    }

    @Transactional
    public void deleteKakaosDivision(DivisionDeleteReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Kakao kakao = kakaoRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_KAKAO_CONSULT));
            if (!kakao.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                kakao.changeDivision(false);

                Division division = divisionService.findDivision(kakao.getDivision().getId());

                division.setDivision_status(DivisionStatus.CANCEL);
                division.setComplete_at(null);

                kakao.setDivision_at(null);
                kakao.setDivision(division);

                kakaoRepository.save(kakao);
                saveDivisionHistory(division, req.getReason());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_KAKAO_CONSULT);
            }
        }
    }

    @Transactional
    public void saveDivisionHistory(Division division, String reason) {
        DivisionHistory divisionHistory = DivisionHistory.builder()
                .division(division)
                .staff_id(division.getStaff_id())
                .reason(reason)
                .division_status(division.getDivision_status())
                .build();

        try {
            divisionService.saveDivisionHistory(divisionHistory);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION_HISTORY);
        }
    }
}
