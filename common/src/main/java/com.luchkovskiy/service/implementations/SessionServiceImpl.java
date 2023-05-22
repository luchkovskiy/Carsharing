package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.SessionRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.SessionService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import com.luchkovskiy.util.ExceptionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final CarRentInfoRepository carRentInfoRepository;

    private final UserRepository userRepository;

    @Override
    public Session read(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Session not found!"));
    }

    @Override
    public List<Session> readAll() {
        return sessionRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Session create(Session object) {
        return sessionRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Session update(Session object) {
        if (!sessionRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Session not found!");
        return sessionRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!sessionRepository.existsById(id))
            throw new EntityNotFoundException("Session not found!");
        sessionRepository.deleteSession(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Session startSession(Session session, CarRentInfo carRentInfo) {
        ExceptionChecker.accountBalanceCheck(session.getUser());
        carRentInfoRepository.save(carRentInfo);
        return sessionRepository.save(session);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Session endSession(Session session, CarRentInfo carRentInfo) {
        subscriptionCheck(session, carRentInfo);
        carRentInfoRepository.save(carRentInfo);
        return sessionRepository.save(session);
    }

    @Override
    public Session findByUser(User user) {
        return sessionRepository.findByUser(user);
    }

    private void subscriptionCheck(Session session, CarRentInfo carRentInfo) {
        User user = userRepository.findById(session.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        Set<Subscription> subscriptions = user.getSubscriptions();
        for (Subscription subscription : subscriptions) {
            if (subscription.getStatus().equals(StatusType.ACTIVE)) {
                if (subscription.getSubscriptionLevel().getAccessLevel() >= carRentInfo.getCar().getCarClassLevel().getAccessLevel()) {
                    subscription.setTripsAmount(subscription.getTripsAmount() + 1);
                } else {
                    user.setAccountBalance(user.getAccountBalance() - session.getTotalPrice());
                }
            }
        }
    }

}
