package com.luchkovskiy.repository;

import com.luchkovskiy.models.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {

    @Modifying
    @Query(value = "DELETE FROM UserCard u WHERE u.id = :linkId")
    void unlinkPaymentCard(Long linkId);

}
