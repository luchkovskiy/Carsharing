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

}
