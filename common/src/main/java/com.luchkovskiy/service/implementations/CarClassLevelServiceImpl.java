package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarClassLevel;
import com.luchkovskiy.repository.CarClassLevelRepository;
import com.luchkovskiy.service.CarClassLevelService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarClassLevelServiceImpl implements CarClassLevelService {

    private final CarClassLevelRepository carClassLevelRepository;

    private final EntityManager entityManager;

    @Cacheable("carClasses")
    @Override
    public CarClassLevel findById(Long id) {
        return carClassLevelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car class not found!"));
    }

    @Cacheable("carClasses")
    @Override
    public List<CarClassLevel> findAll() {
        return carClassLevelRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarClassLevel create(CarClassLevel object) {
        return carClassLevelRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarClassLevel update(CarClassLevel object) {
        entityManager.clear();
        if (!carClassLevelRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car class not found!");
        return carClassLevelRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!carClassLevelRepository.existsById(id))
            throw new EntityNotFoundException("Car class not found!");
        carsCheck(carClassLevelRepository.findById(id).get());
        carClassLevelRepository.deleteCarClassLevel(id);
    }

    private void carsCheck(CarClassLevel carClassLevel) {
        if (!carClassLevel.getCars().isEmpty()) {
            throw new RuntimeException("Can't remove class because some cars are bound to it");
        }
    }

}
