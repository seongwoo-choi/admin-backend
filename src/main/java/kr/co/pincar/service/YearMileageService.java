package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.entity.newEntity.YearMileage;
import kr.co.pincar.jpa.repository.ProductRepository;
import kr.co.pincar.jpa.repository.YearMileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class YearMileageService {
    private final YearMileageRepository yearMileageRepository;
    private final ProductRepository productRepository;

    public List<YearMileage> readYearMileageByProductId(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        List<YearMileage> yearMileages;
        try {
            yearMileages = yearMileageRepository.findByProduct(product);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_YEAR_MILEAGE);
        }
        return yearMileages;

    }

    /**
     * Buffer Code
     */
    @Transactional
    public void newYearMileage(YearMileage yearMileage) {
        yearMileageRepository.save(yearMileage);
    }
}
