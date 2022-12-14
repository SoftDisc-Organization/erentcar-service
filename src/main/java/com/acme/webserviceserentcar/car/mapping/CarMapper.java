package com.acme.webserviceserentcar.car.mapping;

import com.acme.webserviceserentcar.car.resource.CarResource;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CarMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public CarResource toResource(Car model) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(model, CarResource.class);
    }

    public Page<CarResource> modelListToPage(List<Car> modelList, Pageable pageable) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return new PageImpl<>(mapper.mapList(modelList, CarResource.class), pageable, modelList.size());
    }

    public Car toModel(CreateCarResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Car.class);
    }
    public Car toModel(UpdateCarResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, Car.class);
    }
}
