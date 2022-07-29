package kr.co.pincar.jpa.repository;

import kr.co.pincar.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndStatus(String email, String status);

    Optional<User> findByEmailAndStatus(String email, String status);

    Optional<User> findByIdAndStatus(Long id, String status);
}
