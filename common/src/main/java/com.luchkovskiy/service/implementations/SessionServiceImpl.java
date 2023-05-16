package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.SessionRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final CarRentInfoRepository carRentInfoRepository;

    private final UserRepository userRepository;

    @Override
    public Session read(Long id) {
        return sessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Override
    public List<Session> readAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session create(Session object) {
        return sessionRepository.save(object);
    }

    @Override
    public Session update(Session object) {
        if (!sessionRepository.existsById(object.getId()))
            throw new RuntimeException();
        return sessionRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!sessionRepository.existsById(id))
            throw new RuntimeException();
        sessionRepository.deleteById(id);
    }

    @Override
    public Session startSession(Session session, CarRentInfo carRentInfo) {
        carRentInfoRepository.save(carRentInfo);
        return sessionRepository.save(session);
    }

    @Override
    public Session endSession(Session session, CarRentInfo carRentInfo) {
        User user = userRepository.findById(session.getUser().getId()).orElseThrow(RuntimeException::new);
        user.setAccountBalance(user.getAccountBalance() - session.getTotalPrice());
        carRentInfoRepository.save(carRentInfo);
        return sessionRepository.save(session);
    }
}
