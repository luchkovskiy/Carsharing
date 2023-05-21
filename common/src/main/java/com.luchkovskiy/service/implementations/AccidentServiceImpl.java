package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.repository.AccidentRepository;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
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
        return accidentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Accident not found!"));
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
            throw new EntityNotFoundException("Accident not found!");
        return accidentRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!accidentRepository.existsById(id))
            throw new EntityNotFoundException("Accident not found!");
        accidentRepository.deleteById(id);
    }

}
