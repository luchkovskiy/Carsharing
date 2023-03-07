package com.luchkovskiy.service;

import com.luchkovskiy.domain.Session;
import com.luchkovskiy.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Override
    public Session read(Long id) {
        if (!sessionRepository.checkIdValid(id))
            throw new RuntimeException();
        return sessionRepository.read(id);
    }

    @Override
    public List<Session> readAll() {
        return sessionRepository.readAll();
    }

    @Override
    public Session create(Session object) {
        return sessionRepository.create(object);
    }

    @Override
    public Session update(Session object) {
        if (!sessionRepository.checkIdValid(object.getId()))
            throw new RuntimeException();
        return sessionRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        if (!sessionRepository.checkIdValid(id))
            throw new RuntimeException();
        sessionRepository.delete(id);
    }

    @Override
    public boolean checkIdExist(Long id) {
        return sessionRepository.checkIdValid(id);
    }
}
