package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.entity.enums.CountryType;
import kr.co.pincar.jpa.entity.newEntity.Brand;
import kr.co.pincar.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {
    private final BrandRepository brandRepository;


    public List<Brand> readBrandListByCountry(String country) {
        List<Brand> brandList;
        try {
            brandList = brandRepository.findByCountryType(CountryType.valueOf(country));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_BRAND);
        }

        return brandList;
    }

    public Optional<Brand> readBrandById(Long brandId) {
        Optional<Brand> brand = brandRepository.findById(brandId);
        return brand;
    }

    /**
     * Buffer Code
     */
    @Transactional
    public void newBrand(Brand brand) {
        try {
            brandRepository.save(brand);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_EVENT);
        }

    }
}
