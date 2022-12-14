package com.acme.webserviceserentcar.car.api;

import com.acme.webserviceserentcar.car.resource.CarResource;
import com.acme.webserviceserentcar.car.resource.create.CreateCarResource;
import com.acme.webserviceserentcar.car.resource.searchFilters.SearchCarFilters;
import com.acme.webserviceserentcar.car.resource.update.UpdateCarResource;
import com.acme.webserviceserentcar.car.domain.service.CarService;
import com.acme.webserviceserentcar.car.mapping.CarMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin
public class CarController {
    private final CarService carService;
    private final CarMapper mapper;

    public CarController(CarService carService, CarMapper mapper) {
        this.carService = carService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get All Cars", description = "Get All Cars", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Cars returned",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResource.class))
                    ))
    })
    @GetMapping
    public Page<CarResource> getAllCars(Pageable pageable) {
        return mapper.modelListToPage(carService.getAll(), pageable);
    }

    @Operation(summary = "Get All Client Cars", description = "Get All Client Cars", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Client Cars returned",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResource.class))
                    ))
    })
    @GetMapping("/client")
    public Page<CarResource> getAllCarsByClient(Pageable pageable) {
        return mapper.modelListToPage(carService.getAllByClient(), pageable);
    }

    @Operation(summary = "Get Car By Id", description = "Get Car by Id", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResource.class)
                    ))
    })
    @GetMapping("{carId}")
    public CarResource getCarById(@PathVariable Long carId) {
        return mapper.toResource(carService.getById(carId));
    }

    @Operation(summary = "Search Cars", description = "Search Cars", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found cars",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CarResource.class))
                    ))
    })
    @PostMapping("/search")
    public Page<CarResource> searchCars(@Valid @RequestBody SearchCarFilters searchCarFilters, Pageable pageable) {
        return mapper.modelListToPage(carService.searchByFilters(searchCarFilters), pageable);
    }

    @Operation(summary = "Create Car", description = "Create Car", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResource.class)
                    ))
    })
    @PostMapping()
    public CarResource createCar(@Valid @RequestBody CreateCarResource request) {
        return mapper.toResource(carService.create(request));
    }

    @Operation(summary = "Update Car", description = "Updating Car", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResource.class)
                    ))
    })
    @PutMapping("{carId}")
    public CarResource updateCar(@PathVariable Long carId,
                                 @Valid @RequestBody UpdateCarResource request) {
        return mapper.toResource(carService.update(carId, request));
    }

    /*@Operation(summary = "Update Car Rate", description = "Updating Car Rate", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car Rate updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarResource.class)
                    ))
    })
    @PutMapping("{carId}")
    public CarResource updateCarRate(@PathVariable Long carId, @RequestParam(name = "rate") int rate) {
        return mapper.toResource(carService.updateRate(carId, rate));
    }*/

    @Operation(summary = "Delete Car", description = "Delete Car", tags = {"Cars"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car deleted",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable Long carId) {
        return carService.delete(carId);
    }
}
