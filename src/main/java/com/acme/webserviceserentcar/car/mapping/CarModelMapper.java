package com.acme.webserviceserentcar.car.mapping;

import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.resource.*;
import com.acme.webserviceserentcar.car.resource.create.CreateCarModelResource;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarModelResource;
import com.acme.webserviceserentcar.shared.mapping.EnhancedModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class CarModelMapper implements Serializable {
    @Autowired
    private EnhancedModelMapper mapper;

    //Object Mapping

    public CarModelResource toResource(CarModel model) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return mapper.map(model, CarModelResource.class);
    }

    public Page<CarModelResource> modelListToPage(List<CarModel> modelList, Pageable pageable) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return new PageImpl<>(mapper.mapList(modelList, CarModelResource.class), pageable, modelList.size());
    }

    public CarModel toModel(@Valid CreateCarModelResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, CarModel.class);
    }
    public CarModel toModel(@Valid UpdateCarModelResource resource) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(resource, CarModel.class);
    }
}
