package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarClass;
import com.luchkovskiy.repository.CarClassRepository;
import com.luchkovskiy.service.CarClassService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarClassServiceImpl implements CarClassService {

    private final CarClassRepository carClassRepository;

    @Cacheable("carClasses")
    @Override
    public CarClass read(Long id) {
        return carClassRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car class not found!"));
    }

    @Cacheable("carClasses")
    @Override
    public List<CarClass> readAll() {
        return carClassRepository.findAll();
    }

    @Override
    public CarClass create(CarClass object) {
        return carClassRepository.save(object);
    }

    @Override
    public CarClass update(CarClass object) {
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
