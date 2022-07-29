package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.buffer.*;
import kr.co.pincar.jpa.dto.product.*;
import kr.co.pincar.jpa.entity.enums.CountryType;
import kr.co.pincar.jpa.entity.enums.PeriodType;
import kr.co.pincar.jpa.entity.enums.PurchaseType;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.*;
import kr.co.pincar.jpa.repository.ProductRepository;
import kr.co.pincar.jpa.repository.TrimOptionRepository;
import kr.co.pincar.jpa.repository.TrimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final DepositService depositService;
    private final ContractPeriodService contractPeriodService;
    private final PrepaymentService prepaymentService;
    private final YearMileageService yearMileageService;

    private final TrimRepository trimRepository; // Buffer Code
    private final TrimOptionRepository trimOptionRepository;

    public Product findProduct(Long id) {
        return productRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));
    }

    public Product readProductById(Long id) {
        Product product = productRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));
        return product;
    }

    public ProductListRes readProductList(String country, Long brandId, Long productId) {
        List<String> countryList = new ArrayList<>();
        countryList.add("DOMESTIC");
        countryList.add("IMPORTED");

        List<Brand> brandList = brandService.readBrandListByCountry(country);
        List<BrandDto> brandDtoList = brandList.stream().map(brand -> {
            return BrandDto.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .image(brand.getImage())
                    .build();

        }).collect(Collectors.toList());

        Brand brand = brandService.readBrandById(brandId)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_BRAND));


        List<Product> productList = productRepository.findByBrandAndStatus(brand, Status.ACTIVE);

        List<ProductDto> productDtoList = productList.stream().map(product -> {
            return ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .image(product.getImage())
                    .build();

        }).collect(Collectors.toList());

        // 계약기간 조회
        List<ContractPeriod> periodList = contractPeriodService.readPeriodListByProductId(productId);
        List<PeriodDto> periodDtoList = periodList.stream().map(period -> {
            return PeriodDto.builder()
                    .id(period.getId())
                    .viewText(period.getPeriodType().getPeriod()) //디비-TF_MONTH 출력-24개월
                    .build();

        }).collect(Collectors.toList());

        // 연간 주행 거리
        List<YearMileage> yearMileages = yearMileageService.readYearMileageByProductId(productId);
        List<YearMileageDto> yearMileageDtoList = yearMileages.stream().map(yearMileage -> {
            return YearMileageDto.builder()
                    .id(yearMileage.getId())
                    .viewText(yearMileage.getViewText())
                    .build();

        }).collect(Collectors.toList());

        // 보증금
        List<Deposit> depositList = depositService.readDepositListByProductId(productId);
        List<DepositDto> depositDtoList = depositList.stream().map(deposit -> {
            return DepositDto.builder()
                    .id(deposit.getId())
                    .viewText(deposit.getView_text())
                    .build();

        }).collect(Collectors.toList());

        // 선납금
        List<Prepayment> prepaymentList = prepaymentService.readPrepaymentListByProductId(productId);
        List<PrepaymentDto> prepaymentDtoList = prepaymentList.stream().map(prepayment -> {
            return PrepaymentDto.builder()
                    .id(prepayment.getId())
                    .viewText(prepayment.getView_text())
                    .build();

        }).collect(Collectors.toList());

        return ProductListRes.builder()
                .countryList(countryList)
                .brandDto(brandDtoList)
                .productDto(productDtoList)
                .periodDto(periodDtoList)
                .yearMileageDto(yearMileageDtoList)
                .depositDto(depositDtoList)
                .prepaymentDto(prepaymentDtoList)
                .build();

    }


    /**
     * Product Buffer Code
     */
    @Transactional
    public void createNewBrand(BrandBufferDto brandBufferDto) {
        Brand newBrand = Brand.builder()
                .countryType(CountryType.valueOf(brandBufferDto.getCountryType()))
                .name(brandBufferDto.getName())
                .image(brandBufferDto.getImage())
                .build();
        newBrand.setStatus(Status.ACTIVE);
        brandService.newBrand(newBrand);
    }

    @Transactional
    public void createNewProduct(ProductBufferDto productBufferDto, Long id) {
        Brand findBrand = brandService.readBrandById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_BRAND));

        Product newProduct = Product.builder()
                .purchaseType(PurchaseType.valueOf(productBufferDto.getPurchaseType()))
                .name(productBufferDto.getName())
                .image(productBufferDto.getImage())
                .price(productBufferDto.getPrice())
                .brand(findBrand)
                .build();

        newProduct.setStatus(Status.ACTIVE);

        productRepository.save(newProduct);
    }

    @Transactional
    public void createNewTrim(TrimBufferDto trimBufferDto, Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        int size = trimBufferDto.getPrices().size();
        for (int i = 0; i < size; i++) {
            Trim newTrim = Trim.builder()
                    .name(trimBufferDto.getNames().get(i))
                    .price(trimBufferDto.getPrices().get(i))
                    .product(findProduct)
                    .build();
            trimRepository.save(newTrim);
        }
    }

    @Transactional
    public void createNewTrimOption(TrimOptionBufferDto trimOptionBufferDto, Long id) {
        Trim findTrim = trimRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT)); // 그냥 아무코드나 넣음 추가귀찮

        int size = trimOptionBufferDto.getNames().size();
        for (int i = 0; i < size; i++) {
            TrimOption newTrimOption = TrimOption.builder()
                    .name(trimOptionBufferDto.getNames().get(i))
                    .price(trimOptionBufferDto.getPrices().get(i))
                    .trim(findTrim)
                    .build();
            trimOptionRepository.save(newTrimOption);
        }
    }

//    public void createNewColor(ColorBufferDto colorBufferDto, Long id) {
//    }

    @Transactional
    public void createNewPeriod(ContPeriodBufferDto contPeriodBufferDto, Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        int size = contPeriodBufferDto.getPeriodTypes().size();
        for (int i = 0; i < size; i++) {
            ContractPeriod newCP = ContractPeriod.builder()
                    .periodType(PeriodType.valueOf(contPeriodBufferDto.getPeriodTypes().get(i)))
                    .product(findProduct)
                    .build();
            newCP.setStatus(Status.ACTIVE);
            contractPeriodService.newContractPeriod(newCP);
        }
    }

    @Transactional
    public void createNewMileage(YMBufferDto ymBufferDto, Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        int size = ymBufferDto.getPrices().size();
        for (int i = 0; i < size; i++) {
            YearMileage newYM = YearMileage.builder()
                    .viewText(ymBufferDto.getViewTexts().get(i))
                    .price(ymBufferDto.getPrices().get(i))
                    .product(findProduct)
                    .build();
            newYM.setStatus(Status.ACTIVE);
            yearMileageService.newYearMileage(newYM);
        }
    }

    @Transactional
    public void createNewDeposit(DepositBufferDto depositBufferDto, Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        int size = depositBufferDto.getPrices().size();
        for (int i = 0; i < size; i++) {
            Deposit newDeposit = Deposit.builder()
                    .view_text(depositBufferDto.getViewTexts().get(i))
                    .price(depositBufferDto.getPrices().get(i))
                    .product(findProduct)
                    .build();
            newDeposit.setStatus(Status.ACTIVE);
            depositService.newDeposit(newDeposit);
        }

    }

    @Transactional
    public void createNewPayment(PrepaymentBufferDto prepaymentBufferDto, Long id) {
        Product findProduct = productRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        int size = prepaymentBufferDto.getPrices().size();
        for (int i = 0; i < size; i++) {
            Prepayment newPayment = Prepayment.builder()
                    .view_text(prepaymentBufferDto.getViewTexts().get(i))
                    .price(prepaymentBufferDto.getPrices().get(i))
                    .product(findProduct)
                    .build();
            newPayment.setStatus(Status.ACTIVE);
            prepaymentService.newPrepayment(newPayment);
        }
    }

}
