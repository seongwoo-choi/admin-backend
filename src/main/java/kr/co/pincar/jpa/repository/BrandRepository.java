package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.enums.CountryType;
import kr.co.pincar.jpa.entity.newEntity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    List<Brand> findByCountryType(CountryType country);
}
