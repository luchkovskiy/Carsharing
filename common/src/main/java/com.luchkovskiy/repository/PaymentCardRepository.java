package com.luchkovskiy.repository;

import com.luchkovskiy.models.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    @Query("DELETE FROM PaymentCard p WHERE p.id = :id")
    @Modifying
    void deletePaymentCard(Long id);

    boolean existsByCardNumber(String cardNumber);

}
