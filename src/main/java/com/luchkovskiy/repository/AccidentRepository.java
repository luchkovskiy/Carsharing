package com.luchkovskiy.repository;

import com.luchkovskiy.models.Accident;
import org.springframework.data.jpa.repository.*;


public interface AccidentRepository extends JpaRepository<Accident, Long> {


}
