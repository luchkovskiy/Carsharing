package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import com.luchkovskiy.controllers.requests.update.SessionUpdateRequest;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.CarClassLevel;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.SessionService;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import com.luchkovskiy.util.ExceptionChecker;
import com.luchkovskiy.util.LocationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/rest/sessions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Session Controller", description = "This controller allows basic CRUD operations for Sessions and other functionality")
public class SessionController {

    private final SessionService sessionService;

    private final ConversionService conversionService;

    private final CarService carService;

    private final UserService userService;

    private final LocationManager locationManager;


    @Operation(
            summary = "Spring Data Find Session By Id",
            description = "This method returns a session from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Session.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Session> read(@PathVariable("id") @Parameter(description = "Session ID in database", required = true, example = "1")
                                        @NotNull @Min(1) Long id) {
        Session session = sessionService.read(id);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Sessions",
            description = "This method returns an array of all sessions in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Sessions successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<Session> sessions = sessionService.readAll();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New Session",
            description = "This method adds new session in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Car's Id in database")),
                    @Parameter(name = "startTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the session started")),
                    @Parameter(name = "endTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T19:14:56", type = "date-time",
                                    description = "The time when the session ended")),
                    @Parameter(name = "status", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE", type = "statusType",
                                    implementation = StatusType.class, description = "Session status")),
                    @Parameter(name = "totalPrice", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "120.2", type = "number",
                                    description = "Total price of the session")),
                    @Parameter(name = "distancePassed", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "15.5", type = "number",
                                    description = "Amount of kilometers passed during the session"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
            }
    )
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Session> create(@Valid @Parameter(hidden = true) @ModelAttribute SessionCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Session session = conversionService.convert(request, Session.class);
        Session createdSession = sessionService.create(session);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update Session",
            description = "This method updates an existing session and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the session")),
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User's Id in database")),
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Car's Id in database")),
                    @Parameter(name = "startTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T17:24:01", type = "date-time",
                                    description = "The time when the session started")),
                    @Parameter(name = "endTime", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-02-22T19:14:56", type = "date-time",
                                    description = "The time when the session ended")),
                    @Parameter(name = "status", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE", type = "statusType",
                                    implementation = StatusType.class, description = "Session status")),
                    @Parameter(name = "totalPrice", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "120.2", type = "number",
                                    description = "Total price of the session")),
                    @Parameter(name = "distancePassed", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "15.5", type = "number",
                                    description = "Amount of kilometers passed during the session"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Session> update(@Valid @Parameter(hidden = true) @ModelAttribute SessionUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        Session session = conversionService.convert(request, Session.class);
        Session updatedSession = sessionService.update(session);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete Session",
            description = "This method deletes session from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN"})
    public void delete(@PathVariable("id") @Parameter(description = "Session ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        sessionService.delete(id);
    }

    @Operation(
            summary = "Start session",
            description = "This method creates new session, here can null-value parameters, such as totalPrice, distance etc.",
            parameters = {
                    @Parameter(name = "carId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Car id in database"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session started",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "User doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Car doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping("/start")
    public ResponseEntity<Session> startSession(Principal principal, Long carId) {
        ExceptionChecker.authCheck(principal);
        Session session = new Session();
        Car car = carService.read(carId);
        carCheck(car);
        session.setCar(car);
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        ExceptionChecker.ableToPayCheck(user);
        ExceptionChecker.verifyCheck(user);
        activeSessionCheck(user);
        session.setUser(user);
        session.setStatus(StatusType.ACTIVE);
        CarRentInfo carRentInfo = car.getCarRentInfo();
        session.setStartLocation(carRentInfo.getCurrentLocation());
        session.setStartTime(LocalDateTime.now());
        carRentInfo.setAvailable(false);
        carRentInfo.setCurrentLocation(null);
        Session startSession = sessionService.startSession(session, carRentInfo);
        return new ResponseEntity<>(startSession, HttpStatus.OK);
    }

    @Operation(
            summary = "End session",
            description = "This method updates current session and fills it with required parameters",
            parameters = {
                    @Parameter(name = "location", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mohilev, Pervomayskaya 21", type = "string",
                                    description = "Current car's location"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Session ended",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Session.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Session doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
            }
    )
    @PostMapping("/end")
    public ResponseEntity<Session> endSession(Principal principal, String location) {
        ExceptionChecker.authCheck(principal);
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        ExceptionChecker.verifyCheck(user);
        Session readedSession = getActiveSession(user);
        Map<String, Float> sessionInfo = locationManager.getSessionInfo(readedSession.getStartLocation(), location);
        Float sessionDistance = sessionInfo.get("distance");
        Float sessionDuration = sessionInfo.get("duration");
        setSessionInfo(readedSession, sessionDistance);
        Car readedCar = readedSession.getCar();
        CarClassLevel carClassLevel = readedCar.getCarClassLevel();
        CarRentInfo carRentInfo = readedSession.getCar().getCarRentInfo();
        setRentInfo(location, readedSession, readedCar, carRentInfo);
        readedSession.setTotalPrice(sessionDuration * carClassLevel.getPricePerHour());
        Session session = sessionService.endSession(readedSession, carRentInfo);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    private void setSessionInfo(Session readedSession, Float sessionDistance) {
        readedSession.setDistancePassed(sessionDistance);
        readedSession.setStatus(StatusType.FINISHED);
        readedSession.setEndTime(LocalDateTime.now());
        readedSession.setChanged(LocalDateTime.now());
    }

    private void setRentInfo(String location, Session readedSession, Car readedCar, CarRentInfo carRentInfo) {
        float gasRemaining = carRentInfo.getGasRemaining() - readedSession.getDistancePassed() / 100 * readedCar.getGasConsumption();
        if (gasRemaining < 0) {
            carRentInfo.setGasRemaining(0f);
        } else {
            carRentInfo.setGasRemaining(gasRemaining);
        }
        carRentInfo.setAvailable(true);
        carRentInfo.setCurrentLocation(location);
        carRentInfo.setChanged(LocalDateTime.now());
    }

    private void activeSessionCheck(User user) {
        Set<Session> sessions = user.getSessions();
        for (Session userSession : sessions) {
            if (userSession.getStatus().equals(StatusType.ACTIVE)) {
                throw new RuntimeException("You can't start new session while another one is active!");
            }
        }
    }

    private Session getActiveSession(User user) {
        Set<Session> sessions = user.getSessions();
        for (Session session : sessions) {
            if (session.getStatus().equals(StatusType.ACTIVE))
                return session;
        }
        throw new RuntimeException("Active session not found");
    }

    private void carCheck(Car car) {
        if (!car.getVisible()) {
            throw new RuntimeException("Car isn't ready for rent");
        } else if(car.getCarRentInfo().getGasRemaining() <= 0) {
            throw new RuntimeException("Too low gas in the car");
        }
    }
}
