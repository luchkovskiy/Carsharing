package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.service.CarRentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentInfoServiceImpl implements CarRentInfoService {

    private final CarRentInfoRepository carRentInfoRepository;

    @Override
    public CarRentInfo read(Long id) {
        return carRentInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Override
    public List<CarRentInfo> readAll() {
        return carRentInfoRepository.findAll();
    }

    @Override
    public CarRentInfo create(CarRentInfo object) {
        return carRentInfoRepository.save(object);
    }

    @Override
    public CarRentInfo update(CarRentInfo object) {
        if (!carRentInfoRepository.existsById(object.getId()))
            throw new RuntimeException();
        return carRentInfoRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!carRentInfoRepository.existsById(id))
            throw new RuntimeException();
        carRentInfoRepository.deleteById(id);
    }

    @Override
    public CarRentInfo readByCarId(Long carId) {
        return carRentInfoRepository.readCarRentInfoByCarId(carId);
    }
}
