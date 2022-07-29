package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.mainProduct.*;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.*;
import kr.co.pincar.jpa.entity.newEntity.*;
import kr.co.pincar.jpa.repository.MainProductRepository;
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
public class MainProductService {
    private final MainProductRepository mainProductRepository;
    private final UserService userService;
    private final OwnProductService ownProductService;

    public Boolean existsById(Long id) {
        Boolean mainProduct = mainProductRepository.existsByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST));

        return mainProduct;
    }

    public MainProductList readMainProductListById(Long id) {
        return mainProductRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST));

    }

    @Transactional
    public void createMainProduct(MainProductCreateReq req) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        OwnProductList ownProductList = ownProductService.readOwnProductListById(req.getOwnProductListId());

        MainProductList mainProductList = mainProductRepository.findFirstByMainProductTypeAndStatusOrderByDisplayOrderDesc(MainProductType.valueOf(req.getMainProductType()),Status.ACTIVE)
                                            .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST));



        try {
            mainProductRepository.save(MainProductList.builder()
                    .user(user)
                    .mainProductType(MainProductType.valueOf(req.getMainProductType()))
                    .image(req.getMainProductImage())
                    .ownProductList(ownProductList)
                    .vehicleStatus(VehicleStatus.ACTIVE)
                    .displayOrder(mainProductList.getDisplayOrder()+1)
                    .build()
            );

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MAIN_PRODUCT_LIST);
        }

    }

    @Transactional
    public void deleteMainProduct(Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        MainProductList mainProductList = readMainProductListById(id);

        if (!user.getId().equals(mainProductList.getUser().getId())) {
            throw new BaseException(BaseResponseCode.INVALID_USER);
        }

        try {
            mainProductList.changeStatus();
            mainProductRepository.save(mainProductList);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MAIN_PRODUCT_LIST);
        }
    }

    @Transactional
    public void updateMainProductStatus(Long id, String status) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        MainProductList mainProductList = readMainProductListById(id);

        if (!user.getId().equals(mainProductList.getUser().getId())) {
            throw new BaseException(BaseResponseCode.INVALID_USER);
        }

        try {
            mainProductList.changeVehicleStatus(VehicleStatus.valueOf(status));
            mainProductRepository.save(mainProductList);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MAIN_PRODUCT_LIST);
        }
    }


    public List<MainProductListRes> readMainProductList(String type){
        List<MainProductList> mainProductLists;
        try {
//            mainProductLists = mainProductRepository.findByMainProductTypeAndStatusOrderByDisplayOrder(MainProductType.valueOf(type),Status.ACTIVE);
            mainProductLists = mainProductRepository.findByMainProductTypeAndStatusOrderByDisplayOrder(MainProductType.valueOf(type),Status.ACTIVE);

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST);
        }

        return mainProductLists.stream().map(mainProductList -> {
            return MainProductListRes.builder()
                    .displayOrder(mainProductList.getDisplayOrder())
                    .mainProductListId(mainProductList.getId())
                    .image(mainProductList.getImage())
                    .name(mainProductList.getOwnProductList().getProduct().getName())
                    .price(mainProductList.getOwnProductList().getProduct().getPrice())
                    .contractPeriod(mainProductList.getOwnProductList().getContractPeriod().getPeriodType().getPeriod())
                    .deposit(mainProductList.getOwnProductList().getDeposit().getView_text())
                    .prepayment(mainProductList.getOwnProductList().getPrepayment().getView_text())
                    .rentalFee(10000)
                    .vehicleStatus(mainProductList.getVehicleStatus().name())
                    .build();

        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateMainProductsOrder(DisplayOrderUpdateReq req, String type) {
        // Null로 초기화하기
        List<MainProductList> mainProductLists;
        try {
            System.out.println("***");
            mainProductLists = mainProductRepository.findByMainProductTypeAndStatus(MainProductType.valueOf(type), Status.ACTIVE);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST);
        }

        try {
            for(int i = 0 ; i < mainProductLists.size();i++){
                mainProductLists.get(i).changeOrderToNull();
            }
            System.out.println("***2");
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST);
        }

        for (int i = 0; i < req.getOrderUpdateList().size(); i++) {
            // 하나씩 받아서 id로 조회
            MainProductList mainProductList = mainProductRepository.findByMainProductTypeAndIdAndStatus(MainProductType.valueOf(type), req.getOrderUpdateList().get(i).getMainProductListId(), Status.ACTIVE)
                    .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_MAIN_PRODUCT_LIST));

            try {

                mainProductList.displayMainProductList(req.getOrderUpdateList().get(i).getDisplayOrder());
                mainProductRepository.save(mainProductList);
                System.out.println("***3");
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_MAIN_PRODUCT_LIST);
            }
        }
    }
}
