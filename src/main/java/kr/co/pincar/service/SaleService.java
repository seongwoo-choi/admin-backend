package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.sale.SaleReq;
import kr.co.pincar.jpa.dto.sale.SalePageRes;
import kr.co.pincar.jpa.dto.sale.SaleRes;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.enums.PurchaseType;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Division;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.entity.newEntity.Sale;
import kr.co.pincar.jpa.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final DivisionService divisionService;
    private final EntityManager em;

    @Transactional
    public void createSale(SaleReq req) {
        Product product = productService.findProduct(req.getProduct_id());
        Division division = divisionService.findDivision(req.getDivision_id());

        // 디비전 status 가 COMPLETE 일 때 등록 가능
        if (division.getDivision_status() != DivisionStatus.COMPLETE) {
            throw new BaseException(BaseResponseCode.INVALID_DIVISION_STATUS);
        }

        try {
            saleRepository.save(Sale.builder()
                    .price(req.getPrice())
                    .product(product)
                    .division(division)
                    .purchaseType(PurchaseType.valueOf(req.getPurchase_type()))
                    .build());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SALE);
        }
    }


    public SalePageRes readSales(Pageable pageable) {
        Page<Sale> salePage = saleRepository.findByStatus(Status.ACTIVE, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));

        List<SaleRes> saleReqList = new ArrayList<>();

        for (Sale sale : salePage.getContent()) {
            saleReqList.add(SaleRes.builder()
                    .price(sale.getPrice())
                    .product_name(sale.getProduct().getName())
                    .purchase_type(sale.getPurchaseType().name())
                    .brand(sale.getProduct().getBrand().getName())
                    .countryType(sale.getProduct().getBrand().getCountryType().name())
                    .division_id(sale.getDivision().getId())
                    .staff_id(sale.getDivision().getStaff_id())
                    .build());
        }

        return SalePageRes.builder()
                .totalPage(salePage.getTotalPages())
                .saleReqList(saleReqList)
                .build();
    }

    public SaleRes readSale(Long id) {
        Sale sale = saleRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SALE));

        return SaleRes.builder()
                .price(sale.getPrice())
                .product_name(sale.getProduct().getName())
                .purchase_type(sale.getPurchaseType().name())
                .brand(sale.getProduct().getBrand().getName())
                .countryType(sale.getProduct().getBrand().getCountryType().name())
                .division_id(sale.getDivision().getId())
                .staff_id(sale.getDivision().getStaff_id())
                .build();

    }


    @Transactional
    public void updateSale(Long id, SaleReq req) {

        Sale sale = saleRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SALE));

        Product product = productService.findProduct(req.getProduct_id());
        Division division = divisionService.findDivision(req.getDivision_id());

        sale.changeSale(req.getPrice(), product, division, PurchaseType.valueOf(req.getPurchase_type()));

        try {
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SALE);
        }
    }

    @Transactional
    public void deleteSale(List<Long> idList) {
        List<Sale> saleList = new ArrayList<>();

        for (Long id : idList) {
            Sale sale = saleRepository.findByIdAndStatus(id, Status.ACTIVE).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SALE));
            sale.changeStatus();

            saleList.add(sale);
        }
        try {
            saleRepository.saveAll(saleList);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SALE);
        }
    }
}
