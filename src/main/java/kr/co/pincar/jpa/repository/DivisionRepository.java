package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.newEntity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<Division,Long> {
}
