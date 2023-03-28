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
        return carRentInfoRepository.read(id);
    }

    @Override
    public List<CarRentInfo> readAll() {
        return carRentInfoRepository.readAll();
    }

    @Override
    public CarRentInfo create(CarRentInfo object) {
        return carRentInfoRepository.create(object);
    }

    @Override
    public CarRentInfo update(CarRentInfo object) {
        return carRentInfoRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        carRentInfoRepository.delete(id);
    }
}
