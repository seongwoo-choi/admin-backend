package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductListRes;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductPageRes;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductReq;
import kr.co.pincar.jpa.entity.User;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.enums.VehicleStatus;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.jpa.entity.newEntity.*;
import kr.co.pincar.jpa.repository.*;
import kr.co.pincar.jpa.repository.ownProduct.OwnProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnProductService {
    private final OwnProductRepository ownProductRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ContractPeriodRepository contractPeriodRepository;
    private final PrepaymentRepository prepaymentRepository;
    private final DepositRepository depositRepository;
    private final YearMileageRepository yearMileageRepository;

    public OwnProductList readOwnProductListById(Long id) {
        return ownProductRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_OWN_PRODUCT_LIST));

    }

    @Transactional
    public void createOwnProduct(OwnProductReq ownProductReq) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);

        Product product = productService.readProductById(ownProductReq.getProductId());

        ContractPeriod contractPeriod = contractPeriodRepository.findByIdAndStatus(ownProductReq.getContractPeriodId(), Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_CONTRACT_PERIOD));

        YearMileage yearMileage = yearMileageRepository.findByIdAndStatus(ownProductReq.getYearMileageId(), Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_YEAR_MILEAGE));

        Deposit deposit = depositRepository.findByIdAndStatus(ownProductReq.getDepositId(), Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_DEPOSIT));

        Prepayment prepayment = prepaymentRepository.findByIdAndStatus(ownProductReq.getPrepaymentId(), Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PREPAYMENT));

        try {
            ownProductRepository.save(OwnProductList.builder()
                    .vehicleType(VehicleType.valueOf(ownProductReq.getCarType()))
                    .vehicleStatus(VehicleStatus.valueOf("HOLD"))
                    .user(user)
                    .product(product)
                    .contractPeriod(contractPeriod)
                    .yearMileage(yearMileage)
                    .deposit(deposit)
                    .prepayment(prepayment)
                    .build()
            );

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_OWN_PRODUCT_LIST);
        }
    }

    public OwnProductPageRes readOwnProductList(Pageable pageable, String type) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        User user = userService.readAdmin(email);
        Page<OwnProductList> ownProductLists;
        if(type.equals("ALL")){
            ownProductLists = ownProductRepository.findByStatus(Status.ACTIVE, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));

        }else{
            ownProductLists = ownProductRepository.findByVehicleTypeAndStatus(VehicleType.valueOf(type), Status.ACTIVE, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()));
        }

        List<OwnProductListRes> ownProductListResList = ownProductLists.stream().map(ownProduct -> {

            return OwnProductListRes.builder()
                    .ownProductListId(ownProduct.getId())
                    .carType(ownProduct.getVehicleType().name())
                    .productName(ownProduct.getProduct().getName())
                    .productPrice(ownProduct.getProduct().getPrice())
                    .productImage(ownProduct.getProduct().getImage())
                    .contractPeriod(ownProduct.getContractPeriod().getPeriodType().getPeriod())
                    .yearMileage(ownProduct.getYearMileage().getViewText())
                    .deposit(ownProduct.getDeposit().getView_text())
                    .prepayment(ownProduct.getPrepayment().getView_text())
                    .build();
        }).collect(Collectors.toList());

        return OwnProductPageRes.builder()
                .totalPage(ownProductLists.getTotalPages())
                .ownProductListResList(ownProductListResList)
                .build();
    }

    public boolean existsById(Long ownProductListId) {
        return ownProductRepository.existsByIdAndStatus(ownProductListId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_OWN_PRODUCT_LIST));
    }

    public List<OwnProductListRes> findAllOwnProductByVehicleType(String vehicleType) {
        return ownProductRepository.findAllOwnProductByVehicleType(VehicleType.valueOf(vehicleType));
    }

    public OwnProductListRes findOneOwnProduct(Long ownProductId) {
        return ownProductRepository.findOneOwnProduct(ownProductId);
    }

}
