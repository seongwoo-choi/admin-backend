package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.newEntity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
