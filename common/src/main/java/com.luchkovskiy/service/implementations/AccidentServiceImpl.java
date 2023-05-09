package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.repository.AccidentRepository;
import com.luchkovskiy.service.AccidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {


    private final AccidentRepository accidentRepository;

    @Override
    public Accident read(Long id) {
        return accidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Accident not found!"));
    }

    @Override
    public List<Accident> readAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Accident create(Accident object) {
        return accidentRepository.save(object);
    }

    @Override
    public Accident update(Accident object) {
        if (!accidentRepository.existsById(object.getId()))
            throw new RuntimeException();
        return accidentRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!accidentRepository.existsById(id))
            throw new RuntimeException();
        accidentRepository.deleteById(id);
    }

}
