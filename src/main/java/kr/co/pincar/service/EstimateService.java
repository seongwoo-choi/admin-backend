package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionDeleteReq;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.division.DivisionStatusUpdateReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.dto.estimate.*;
import kr.co.pincar.jpa.dto.estimate.EstimateCreateReq;
import kr.co.pincar.jpa.entity.enums.ConsultType;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.enums.PurchaseType;
import kr.co.pincar.jpa.entity.newEntity.Division;
import kr.co.pincar.jpa.entity.newEntity.DivisionHistory;
import kr.co.pincar.jpa.entity.newEntity.Estimate;
import kr.co.pincar.jpa.entity.newEntity.Kakao;
import kr.co.pincar.jpa.repository.UserRepository;
import kr.co.pincar.jpa.repository.EstimateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final UserRepository adminRepository;
    private final DivisionService divisionService;
    private final EntityManager em;

    // 견적문의 상담 접수 등록
    @Transactional
    public void createEstimate(EstimateCreateReq req) {
        ConsultType consultType;

        if (isStringEmpty(req.getConsultType())) {
            consultType = ConsultType.CABLE;
        } else {
            consultType = ConsultType.valueOf(req.getConsultType());
        }

        PurchaseType purchaseType = null;
        if (req.getPurchaseType() != null) {
            purchaseType = PurchaseType.valueOf(req.getPurchaseType());
        }

        Estimate es;

        try {
            estimateRepository.save(
                    Estimate.builder()
                            .customer_name(req.getCustomerName())
                            .customer_phone(req.getCustomerPhone())
                            .purchase_type(purchaseType)
                            .cont_time(req.getContTime())
                            .location_type(req.getLocationType())
                            .content(req.getContent())
                            .consult_type(consultType)
                            .personal_info_check(req.getPersonalInfoCheck())
                            .marketing_info_check(req.getMarketingInfoCheck())
                            .build()
            );

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
        }

    }

    // 견적문의 상담접수 조회
    public EstimatePageRes readEstimates(LocalDate startDate, LocalDate endDate, Pageable pageable) {

        Page<Estimate> estimateList;

        LocalDateTime startDatetime = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
        LocalDateTime endDatetime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));

        if (pageable.getPageNumber() < 1) {
            throw new BaseException(BaseResponseCode.INVALID_PAGE);
        }

        try {
            estimateList = estimateRepository.findByCreatedAtBetween(startDatetime, endDatetime, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE_LIST);
        }

        List<EstimateRes> estimateResList = new ArrayList<>();

        estimateList.getContent().forEach((item) -> {

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

            EstimateRes estimateRes = EstimateRes.builder()
                    .id(item.getId())
                    .createdAt(item.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .customerName(item.getCustomer_name())
                    .customerPhone(item.getCustomer_phone())
                    .purchaseType(item.getPurchase_type().toString())
                    .contTime(item.getCont_time())
                    .locationType(item.getLocation_type())
                    .content(item.getContent())
                    .consultType(item.getConsult_type().toString())
                    .personalInfoCheck(personal_info_check)
                    .marketingInfoCheck(marketing_info_check)
                    .divisionComplete(division_complete)
                    .divisionAt(division_at)
                    .build();

            estimateResList.add(estimateRes);
        });

        return EstimatePageRes.builder()
                .totalPage(estimateList.getTotalPages())
                .consultList(estimateResList)
                .build();
    }

    // 견적문의 상담접수 상세조회
    public EstimateRes readEstimate(Long id) {

        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));

        String division_complete;
        String division_at;
        String personal_info_check;
        String marketing_info_check;

        if (estimate.getDivision_complete()) {
            division_complete = "완료";
            division_at = estimate.getDivision_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            division_complete = null;
            division_at = "";
        }

        if (estimate.getPersonal_info_check()) {
            personal_info_check = "동의";
        } else {
            personal_info_check = "미동의";
        }

        if (estimate.getMarketing_info_check()) {
            marketing_info_check = "동의";
        } else {
            marketing_info_check = "미동의";
        }

        return EstimateRes.builder()
                .id(estimate.getId())
                .createdAt(estimate.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .customerName(estimate.getCustomer_name())
                .customerPhone(estimate.getCustomer_phone())
                .purchaseType(estimate.getPurchase_type().toString())
                .contTime(estimate.getCont_time())
                .locationType(estimate.getLocation_type())
                .content(estimate.getContent())
                .consultType(estimate.getConsult_type().toString())
                .personalInfoCheck(personal_info_check)
                .marketingInfoCheck(marketing_info_check)
                .divisionComplete(division_complete)
                .divisionAt(division_at)
                .build();
    }

    // 견적문의 상담접수 수정
    @Transactional
    public void updateEstimate(EstimateUpdateReq req, Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));

        PurchaseType purchaseType = null;
        ConsultType consultType = null;

        if (req.getPurchaseType() != null) {
            purchaseType = PurchaseType.valueOf(req.getPurchaseType());
        }

        if (req.getConsultType() != null) {
            consultType = ConsultType.valueOf(req.getConsultType());
        }
        estimate.changeEstimate(
                req.getCustomerName(),
                req.getCustomerPhone(),
                purchaseType,
                req.getContTime(),
                req.getLocationType(),
                req.getContent(),
                consultType,
                req.getPersonalInfoCheck(),
                req.getMarketingInfoCheck()
        );

        try {
            estimateRepository.save(estimate);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
        }
    }

    // 견적문의 배분
    @Transactional
    public void createEstimateDivision(DivisionReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Estimate estimate = estimateRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));
            if (estimate.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                estimate.changeDivision(true);
                Division division = Division.builder()
                        .staff_id(req.getStaffId())
                        .division_status(DivisionStatus.INPROGRESS)
                        .question(estimate)
                        .build();

                estimate.setDivision(divisionService.saveDivision(division));
                estimateRepository.save(estimate);

                saveDivisionHistory(estimate.getDivision(), null);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
            }
        }
    }

    @Transactional
    public void deleteEstimatesDivision(DivisionDeleteReq req) {
        List<Long> idList = req.getId();

        for (Long id : idList) {
            Estimate estimate = estimateRepository.findById(id)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));
            if (!estimate.getDivision_complete()) {
                throw new BaseException(BaseResponseCode.DIVISION_IS_COMPLETE);
            }
            try {
                estimate.changeDivision(false);

                Division division = divisionService.findDivision(estimate.getDivision().getId());

                division.setDivision_status(DivisionStatus.CANCEL);
                division.setComplete_at(null);

                estimate.setDivision_at(null);
                estimate.setDivision(division);

                estimateRepository.save(estimate);
                saveDivisionHistory(division, req.getReason());
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
            }
        }
    }

    public Boolean existsEstimate(Long id) {
        return estimateRepository.existsById(id);
    }


    public Estimate findEstimate(Long id) {
        return estimateRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ESTIMATE));
    }

    @Transactional
    public void changeEstimateDivision(DivisionStatusUpdateReq req) {
        Estimate estimate = findEstimate(req.getId());

        if (isStringEmpty(req.getDivisionStatus())) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        } else if (req.getDivisionStatus().equals("INPROGRESS")) {
            estimate.getDivision().setDivision_status(DivisionStatus.INPROGRESS);
            estimate.getDivision().setComplete_at(null);
        } else if (req.getDivisionStatus().equals("COMPLETE")) {
            estimate.getDivision().setDivision_status(DivisionStatus.COMPLETE);
            estimate.getDivision().setComplete_at(LocalDate.now());
        } else if (req.getDivisionStatus().equals("CANCEL")) {
            estimate.getDivision().setDivision_status(DivisionStatus.CANCEL);
            estimate.getDivision().setComplete_at(null);
            estimate.setDivision_at(null);
            estimate.getDivision().setStaff_id(null);
        } else {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION_STATUS);
        }

        saveDivisionHistory(estimate.getDivision(), req.getReason());

        try {
            estimateRepository.save(estimate);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ESTIMATE);
        }
    }

    @Transactional
    public void changeStaffId(Long id, DivisionHistoryCreateReq req) {
        if (isStringEmpty(req.getStaffId())) {
            throw new BaseException(BaseResponseCode.INVALID_STAFFID);
        }

        Estimate estimate = findEstimate(id);
        Division division = divisionService.changeStaffId(estimate.getDivision().getId(), req);
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
