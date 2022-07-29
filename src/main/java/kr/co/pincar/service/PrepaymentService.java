package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Prepayment;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.repository.PrepaymentRepository;
import kr.co.pincar.jpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrepaymentService {
    private final PrepaymentRepository prepaymentRepository;
    private final ProductRepository productRepository;

    public List<Prepayment> readPrepaymentListByProductId(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));
//        Product product = productService.readProductById(productId);
        // The dependencies of some of the beans in the application context form a cycle:에러 뜸

        List<Prepayment> prepayments;
        try {
            prepayments = prepaymentRepository.findByProduct(product);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_PREPAYMENT);
        }
        return prepayments;

    }

    /**
     * Buffer Code
     */
    @Transactional
    public void newPrepayment(Prepayment prepayment) {
        prepaymentRepository.save(prepayment);
    }

}
