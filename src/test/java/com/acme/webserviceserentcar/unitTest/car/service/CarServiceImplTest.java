package com.acme.webserviceserentcar.unitTest.car.service;

import com.acme.webserviceserentcar.car.domain.model.entity.Car;
import com.acme.webserviceserentcar.car.domain.model.entity.CarModel;
import com.acme.webserviceserentcar.car.domain.model.enums.InsuranceType;
import com.acme.webserviceserentcar.car.domain.persistence.CarModelRepository;
import com.acme.webserviceserentcar.car.domain.persistence.CarRepository;
import com.acme.webserviceserentcar.car.mapping.CarMapper;
import com.acme.webserviceserentcar.car.persistence.CarRepositoryCustom;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.service.CarServiceImpl;
import com.acme.webserviceserentcar.client.domain.model.entity.Client;
import com.acme.webserviceserentcar.client.domain.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.validation.Validator;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


// @SpringBootTest
class CarServiceImplTest {
   // @Autowired

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarRepositoryCustom carRepositoryCustom;

    @Mock
    private ClientService clientService;

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private Client client;
    private CarModel carModel;

    private CreateCarResource carResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);

        carModel = new CarModel();
        carModel.setId(1L);

        car = new Car();
        car.setId(1L);
        car.setLicensePlate("AKR-079");
        car.setInsurance(InsuranceType.RIMAC);
        car.setCarModel(carModel);
        car.setClient(client);
        car.setActive(true);
    }


    @Test
    void ValidateThatThereIsNoSimilarLicensePlateNumber() {
        // Arrange
        when(carRepository.findByLicensePlate("AKR-079")).thenReturn(car);
        // Act
        boolean result = carService.existThisLicensePlate("AKR-080");
        // Assert
        boolean expected = false;
        assertEquals(expected, result);
    }

    @Test
    void ValidateThatTheSoatIsActiveForLicensePlateNumber() {
        // Arrange
        String licensePlate = "AKR-079";
        InsuranceType insuranceType = InsuranceType.RIMAC;

        // Act
        boolean result = carService.isActiveSOAT(licensePlate, insuranceType);

        // Assert
        boolean expected = true;
        assertEquals(expected, result);
    }

    @Test
    void ValidateThatVehicleWasSuccessfullyRegistered() {
        // Arrange
        CreateCarResource request = new CreateCarResource();
        request.setCarModelId(1L);

        // Act
        when(clientService.getByToken()).thenReturn(client);
        when(carRepository.findByLicensePlate(car.getLicensePlate())).thenReturn(null);
        when(carRepositoryCustom.isActiveSOAT(car.getLicensePlate(), car.getInsurance())).thenReturn(true);
        when(carModelRepository.findById(request.getCarModelId())).thenReturn(Optional.of(car.getCarModel()));
        when(carMapper.toModel(request)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.create(request);

        // Assert
        assertNotNull(result);
    }
}
