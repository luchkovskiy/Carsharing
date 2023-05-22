package com.luchkovskiy.repository;

import com.luchkovskiy.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByUserId(Long userId);

    @Query("DELETE FROM VerificationCode v WHERE v.id = :id")
    @Modifying
    void deleteVerificationCode(Long id);

}
