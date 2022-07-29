package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionDeleteReq;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.division.DivisionStatusUpdateReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.dto.visit.*;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.newEntity.Division;
import kr.co.pincar.jpa.entity.newEntity.DivisionHistory;
import kr.co.pincar.jpa.entity.newEntity.Visit;
import kr.co.pincar.jpa.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static kr.co.pincar.utils.ValidationRegex.isStringEmpty;


@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {
    private final VisitRepository visitRepository;
    private final DivisionService divisionService;
    private final EntityManager em;

    // 방문상담 상담접수 등록
    @Transactional
    public void createVisits(VisitCreateReq req) {
        Visit visit;
        try {
            visit = Visit.builder()
                    .customer_name(req.getCustomerName())
                    .customer_phone(req.getCustomerPhone())
                    .address(req.getAddress())
                    .personal_info_check(req.getPersonalInfoCheck())
                    .marketing_info_check(req.getMarketingInfoCheck())
                    .build();

            visitRepository.save(visit);

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_VISIT_CONSULT);
        }

    }

    // 방문상담 상담접수 수정
    @Transactional
    public void updateVisits(VisitUpdateReq req, Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_VISIT_CONSULT));

        try {
            visit.changeVisit(
                    req.getCustomerName(),
                    req.getCustomerPhone(),
                    req.getAddress(),
                    req.getPersonalInfoCheck(),
                    req.getMarketingInfoCheck());

            visitRepository.save(visit);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_UPDATE_VISIT_CONSULT);
        }

    }

    // 방문상담 상담접수 조회
    public VisitPageRes readVisitsList(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Visit> visitList;

        LocalDateTime startDatetime = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        if(pageable.getPageNumber() < 1) {
            throw new BaseException(BaseResponseCode.INVALID_PAGE);
        }

        try {
            visitList = visitRepository.findByCreatedAtBetween(startDatetime, endDatetime, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE_LIST);
        }

        List<VisitRes> visitResList = new ArrayList<>();

        visitList.getContent().forEach((item) -> {
            String division_complete;
            String division_at;
            String personal_info_check;
            String marketing_info_check;

            if (item.getDivision_complete()) {
                division_complete = "완료";
                division_at = item.getDivision_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                division_complete = null;
                division_at = "";
            }

            if (item.getPersonal_info_check()) {
                personal_info_check = "동의";
            } else {
                personal_info_check = "미동의";
            }

            if (item.getMarketing_info_check()) {
                marketing_info_check = "동의";
            } else {
                marketing_info_check = "미동의";
            }

            VisitRes visitRes = VisitRes.builder()
                    .id(item.getId())
                    .createdAt(item.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .customerName(item.getCustomer_name())
                    .customerPhone(item.getCustomer_phone())
                    .address(item.getAddress())
                    .personalInfoCheck(personal_info_check)
                    .marketingInfoCheck(marketing_info_check)
                    .divisionComplete(division_complete)
                    .divisionAt(division_at)
                    .build();

            visitResList.add(visitRes);
        });

        return VisitPageRes.builder()
                .totalPage(visitList.getTotalPages())
                .consultList(visitResList)
                .build();
    }

    public Boolean existsVisit(Long id) {
        return visitRepository.existsById(id);
    }

    // 방문상담 상담접수 상세 조회
    public VisitRes readVisits(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_VISIT_CONSULT));

        String division_complete;
        String division_at;
        String personal_info_check;
        String marketing_info_check;

        if (visit.getDivision_complete()) {
            division_complete = "완료";
            division_at = visit.getDivision_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            division_complete = null;
            division_at = "";
        }

        if (visit.getPersonal_info_check()) {
            personal_info_check = "동의";
        } else {
            personal_info_check = "미동의";
        }

        if (visit.getMarketing_info_check()) {
            marketing_info_check = "동의";
        } else {
            marketing_info_check = "미동의";
        }

        return VisitRes.builder()
                .id(visit.getId())
                .createdAt(visit.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .customerName(visit.getCustomer_name())
                .customerPhone(visit.getCustomer_phone())
                .address(visit.getAddress())
                .personalInfoCheck(personal_info_check)
                .marketingInfoCheck(marketing_info_check)
                .divisionComplete(division_complete)
                .divisionAt(division_at)
                .build();
    }

    @Transactional
    public void createVisitDivision(DivisionReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Visit visit = visitRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));
            if (visit.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                visit.changeDivision(true);
                Division division = Division.builder()
                        .staff_id(req.getStaffId())
                        .division_status(DivisionStatus.INPROGRESS)
                        .question(visit)
                        .build();

                visit.setDivision(divisionService.saveDivision(division));
                visitRepository.save(visit);
                saveDivisionHistory(visit.getDivision(), null);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
            }
        }
    }

    @Transactional
    public void deleteVisitDivision(DivisionDeleteReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Visit visit = visitRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));
            if (!visit.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                visit.changeDivision(false);

                Division division = divisionService.findDivision(visit.getDivision().getId());

                division.setDivision_status(DivisionStatus.CANCEL);
                division.setComplete_at(null);

                visit.setDivision_at(null);
                visit.setDivision(division);

                visitRepository.save(visit);
                saveDivisionHistory(division, req.getReason());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
            }
        }
    }

    public Visit findVisit(Long id) {
        return visitRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_VISIT_CONSULT));
    }

    @Transactional
    public void changeVisitDivision(DivisionStatusUpdateReq req) {
        Visit visit = findVisit(req.getId());

        if (isStringEmpty(req.getDivisionStatus())) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        } else if (req.getDivisionStatus().equals("INPROGRESS")) {
            visit.getDivision().setDivision_status(DivisionStatus.INPROGRESS);
            visit.getDivision().setComplete_at(null);
        } else if (req.getDivisionStatus().equals("COMPLETE")) {
            visit.getDivision().setDivision_status(DivisionStatus.COMPLETE);
            visit.getDivision().setComplete_at(LocalDate.now());
        } else if (req.getDivisionStatus().equals("CANCEL")) {
            visit.getDivision().setDivision_status(DivisionStatus.CANCEL);
            visit.getDivision().setComplete_at(null);
            visit.setDivision_at(null);
            visit.getDivision().setStaff_id(null);
        } else {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        }

        saveDivisionHistory(visit.getDivision(), req.getReason());

        try {
            visitRepository.save(visit);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
        }
    }

    @Transactional
    public void changeStaffId(Long id, DivisionHistoryCreateReq req) {
        if (isStringEmpty(req.getStaffId())) {
            throw new BaseException(BaseResponseCode.INVALID_STAFFID);
        }

        Visit visit = findVisit(id);
        Division division = divisionService.changeStaffId(visit.getDivision().getId(), req);
        saveDivisionHistory(division, req.getReason());
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
