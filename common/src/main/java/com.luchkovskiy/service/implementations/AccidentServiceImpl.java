package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.AccidentRepository;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.repository.SessionRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final CarRentInfoRepository carRentInfoRepository;

    private final SessionRepository sessionRepository;

    private final EntityManager entityManager;

    @Override
    public Accident findById(Long id) {
        return accidentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Accident not found!"));
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Accident create(Accident object) {
        validCheck(object);
        object.setFine(calculateFine(object.getDamageLevel()));
        object.setRatingSubtraction(calculateRatingSubtraction(object.getDamageLevel()));
        User user = object.getSession().getUser();
        Car car = object.getSession().getCar();
        user.setRating(user.getRating() - object.getRatingSubtraction());
        user.setAccountBalance(user.getAccountBalance() - object.getFine());
        userRepository.save(user);
        carRepository.save(car);
        return accidentRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Accident update(Accident object) {
        entityManager.clear();
        if (!accidentRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Accident not found!");
        validCheck(object);
        return accidentRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!accidentRepository.existsById(id))
            throw new EntityNotFoundException("Accident not found!");
        accidentRepository.deleteAccident(id);
    }

    @Override
    public User getUserFromAccident(Long sessionId) {
        return accidentRepository.getUserFromAccident(sessionId);
    }

    @Override
    public Car getCarFromAccident(Long sessionId) {
        return accidentRepository.getCarFromAccident(sessionId);
    }

    private Float calculateFine(Integer damage) {
        return damage * 20f;
    }

    private Float calculateRatingSubtraction(Integer damage) {
        return damage * 0.5f;
    }


    private void validCheck(Accident accident) {
        sessionCheck(accident);
        criticalCheck(accident);
        timeCheck(accident);
        userCheck(accident);
    }

    private void sessionCheck(Accident accident) {
        if (!sessionRepository.existsById(accident.getSession().getId())) {
            throw new EntityNotFoundException("Session not found");
        }
    }

    private void criticalCheck(Accident accident) {
        if (accident.getCritical()) {
            accident.setDamageLevel(5);
            CarRentInfo carRentInfo = accident.getSession().getCar().getCarRentInfo();
            carRentInfo.setRepairing(true);
            carRentInfo.setAvailable(false);
            carRentInfoRepository.save(carRentInfo);
        }
    }

    private void timeCheck(Accident accident) {
        if (accident.getTime().isBefore(accident.getSession().getStartTime()) || accident.getTime().isAfter(accident.getSession().getEndTime())) {
            throw new RuntimeException("The time of the accident must be within the session");
        }
    }

    private void userCheck(Accident accident) {
        User user = accident.getSession().getUser();
        if (user.getRating() < 2.5) {
            user.setActive(false);
            userRepository.save(user);
        }
    }
}
