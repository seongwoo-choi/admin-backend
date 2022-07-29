package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.entity.enums.Status;
import kr.co.pincar.jpa.entity.newEntity.ContractPeriod;
import kr.co.pincar.jpa.entity.newEntity.Product;
import kr.co.pincar.jpa.repository.ContractPeriodRepository;
import kr.co.pincar.jpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractPeriodService {
    private final ContractPeriodRepository contractPeriodRepository;
    private final ProductRepository productRepository;

    public List<ContractPeriod> readPeriodListByProductId(Long productId) {
        Product product = productRepository.findByIdAndStatus(productId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_PRODUCT));

        List<ContractPeriod> contractPeriods;
        try {
            contractPeriods = contractPeriodRepository.findByProduct(product);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_CONTRACT_PERIOD);
        }
        return contractPeriods;

    }

    /**
     * Buffer Code
     */
    @Transactional
    public void newContractPeriod(ContractPeriod contractPeriod) {
        contractPeriodRepository.save(contractPeriod);
    }
}
