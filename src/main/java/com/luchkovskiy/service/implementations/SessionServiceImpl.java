package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import com.luchkovskiy.service.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

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
