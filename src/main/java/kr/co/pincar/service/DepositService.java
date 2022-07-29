package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.Deposit;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.repository.DepositRepository;
import kr.co.pincar.jpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepositService {
    private final DepositRepository depositRepository;
    private final ProductRepository productRepository;

    public List<Deposit> readDepositListByProductId(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        List<Deposit> depositList;
        try {
            depositList = depositRepository.findByProduct(product);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_DEPOSIT);
        }
        return depositList;

    }

    /**
     * Buffer Code
     */
    @Transactional
    public void newDeposit(Deposit deposit) {
        depositRepository.save(deposit);
    }

}
