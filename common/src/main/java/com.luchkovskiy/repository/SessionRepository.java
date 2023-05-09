package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {

}
