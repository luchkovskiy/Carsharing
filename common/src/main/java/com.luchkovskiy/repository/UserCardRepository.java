package com.luchkovskiy.repository;

import com.luchkovskiy.models.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM UserCard u WHERE u.user.id = :userId and u.paymentCard.id = :cardId")
    void unlinkPaymentCard(Long userId, Long cardId);

}
