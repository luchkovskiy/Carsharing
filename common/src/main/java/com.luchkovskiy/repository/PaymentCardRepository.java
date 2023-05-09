package com.luchkovskiy.repository;

import com.luchkovskiy.models.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {


}
