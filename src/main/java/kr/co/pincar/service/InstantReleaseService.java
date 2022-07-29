package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.instantRelease.*;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.ReleaseStatus;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.InstantRelease;
import kr.co.pincar.jpa.entity.newEntity.OwnProductList;
import kr.co.pincar.jpa.repository.InstantReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstantReleaseService {
    private final InstantReleaseRepository instantReleaseRepository;
    private final UserService userService;
    private final OwnProductService ownProductService;

    public boolean existsById(Long id) {
        return instantReleaseRepository.existsByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_OWN_PRODUCT_LIST));

    }

    @Transactional
    public void createInstantRelease(InstantReleaseReq instantReleaseReq) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        OwnProductList ownProductList = ownProductService.readOwnProductListById(instantReleaseReq.getOwnProductListId());

        try {
            instantReleaseRepository.save(InstantRelease.builder()
                    .user(user)
                    .ownProductList(ownProductList)
                    .image(instantReleaseReq.getImage())
                    .releaseStatus(ReleaseStatus.ACTIVE)
                    .build()
            );
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_INSTANT_RELEASE);
        }

    }

    public InstantReleaseRes readInstantReleaseList() {
        List<InstantRelease> instantReleases;
        try {
            instantReleases = instantReleaseRepository.findByStatusAndDisplayOrderNotNullOrderByDisplayOrder(Status.ACTIVE);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE);
        }

        // null 아닌 것
        List<DisplayList> displayLists = instantReleases.stream().map(instantRelease -> {
            return DisplayList.builder()
                    .displayOrder(instantRelease.getDisplayOrder())
                    .instantReleaseId(instantRelease.getId())
                    .image(instantRelease.getImage())
                    .price(instantRelease.getOwnProductList().getProduct().getPrice())
                    .contractPeriod(instantRelease.getOwnProductList().getContractPeriod().getPeriodType().getPeriod())
                    .deposit(instantRelease.getOwnProductList().getDeposit().getView_text())
                    .prepayment(instantRelease.getOwnProductList().getPrepayment().getView_text())
                    .rentalFee(100000) // 계산
                    .releaseStatus(instantRelease.getReleaseStatus().name())
                    .build();
        }).collect(Collectors.toList());


        List<InstantRelease> instantReleases2;
        try {
            instantReleases2 = instantReleaseRepository.findByDisplayOrderAndStatus(null, Status.ACTIVE);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE);
        }
        // null 인 것
        List<InstantReleaseList> instantReleaseList = instantReleases2.stream().map(instantRelease -> {
            return InstantReleaseList.builder()
                    .instantReleaseId(instantRelease.getId())
                    .image(instantRelease.getImage())
                    .price(instantRelease.getOwnProductList().getProduct().getPrice())
                    .contractPeriod(instantRelease.getOwnProductList().getContractPeriod().getPeriodType().getPeriod())
                    .deposit(instantRelease.getOwnProductList().getDeposit().getView_text())
                    .prepayment(instantRelease.getOwnProductList().getPrepayment().getView_text())
                    .rentalFee(100000) // 계산
                    .releaseStatus(instantRelease.getReleaseStatus().name())
                    .build();
        }).collect(Collectors.toList());

        return InstantReleaseRes.builder()
                .displayList(displayLists)
                .instantReleaseList(instantReleaseList)
                .build();

    }

    @Transactional
    public void deleteInstantRelease(Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        InstantRelease instantRelease = instantReleaseRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE));

        if (!user.getId().equals(instantRelease.getUser().getId())) {
            throw new BaseException(BaseResponseCode.INVALID_ADMIN);
        }

        try {
            instantRelease.changeStatus();
            instantReleaseRepository.save(instantRelease);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_INSTANT_RELEASE);
        }

    }

    @Transactional
    public void updateInstantRelease(Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        InstantRelease instantRelease = instantReleaseRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE));

        if (!user.getId().equals(instantRelease.getUser().getId())) {
            throw new BaseException(BaseResponseCode.INVALID_ADMIN);
        }

        try {
            instantRelease.changeInstantReleaseStatus();
            instantReleaseRepository.save(instantRelease);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_INSTANT_RELEASE);
        }
    }


    @Transactional
    public void updateInstantReleaseOrder(InstantReleaseOrderReq req) {
        // Null 아닌 애들 초기화하기
        List<InstantRelease> instantReleases;
        try {
            instantReleases = instantReleaseRepository.findByStatusAndDisplayOrderNotNullOrderByDisplayOrder(Status.ACTIVE);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE);
        }

        try {
            for(int i = 0 ; i < instantReleases.size();i++){
                instantReleases.get(i).changeOrderToNull();
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE);
        }

        for (int i = 0; i < req.getDisplayOrderList().size(); i++) {
            // 하나씩 받아서 id로 즉시출고 조회
            InstantRelease instantRelease = instantReleaseRepository.findByIdAndStatus(req.getDisplayOrderList().get(i).getInstantReleaseId(), Status.ACTIVE)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_INSTANT_RELEASE));

            // 그 즉시출고에 해당하는 아이를 display
            try {
                instantRelease.displayInstantRelease(req.getDisplayOrderList().get(i).getDisplayOrder());
                instantReleaseRepository.save(instantRelease);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_INSTANT_RELEASE);
            }
        }
    }
}
