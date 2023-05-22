package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentInfoServiceImpl implements CarRentInfoService {

    private final CarRentInfoRepository carRentInfoRepository;

    @Override
    public CarRentInfo read(Long id) {
        return carRentInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car info not found!"));
    }

    @Override
    public List<CarRentInfo> readAll() {
        return carRentInfoRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarRentInfo create(CarRentInfo object) {
        if (carRentInfoRepository.readCarRentInfoByCarId(object.getCar().getId()) != null) {
            throw new RuntimeException("You can't create a second info for the same car");
        } else {
            return carRentInfoRepository.save(object);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public CarRentInfo update(CarRentInfo object) {
        if (!carRentInfoRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Car info not found!");
        return carRentInfoRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!carRentInfoRepository.existsById(id))
            throw new EntityNotFoundException("Car info not found!");
        carRentInfoRepository.deleteCarRentInfo(id);
    }

    @Override
    public CarRentInfo readByCarId(Long carId) {
        CarRentInfo carRentInfo;
        try {
            carRentInfo = carRentInfoRepository.readCarRentInfoByCarId(carId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Car info not found!");
        }
        return carRentInfo;
    }
}
