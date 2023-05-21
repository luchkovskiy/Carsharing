package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarClassLevel;
import com.luchkovskiy.repository.CarClassRepository;
import com.luchkovskiy.service.CarClassLevelService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarClassLevelServiceImpl implements CarClassLevelService {

    private final CarClassRepository carClassRepository;

    @Cacheable("carClasses")
    @Override
    public CarClassLevel read(Long id) {
        return carClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car class not found!"));
    }

    @Cacheable("carClasses")
    @Override
    public List<CarClassLevel> readAll() {
        return carClassRepository.findAll();
    }

    @Override
    public CarClassLevel create(CarClassLevel object) {
        return carClassRepository.save(object);
    }

    @Override
    public CarClassLevel update(CarClassLevel object) {
        if (!carClassRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car class not found!");
        return carClassRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!carClassRepository.existsById(id))
            throw new EntityNotFoundException("Car class not found!");
        carClassRepository.deleteById(id);
    }
}
