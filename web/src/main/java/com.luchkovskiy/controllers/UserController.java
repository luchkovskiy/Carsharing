package com.luchkovskiy.controllers;


import com.google.maps.model.TravelMode;
import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.controllers.requests.create.UserCardCreateRequest;
import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.controllers.requests.update.UserUpdateRequest;
import com.luchkovskiy.controllers.response.CarDistanceResponse;
import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.UserCard;
import com.luchkovskiy.models.VerificationCode;
import com.luchkovskiy.repository.UserCardRepository;
import com.luchkovskiy.repository.VerificationCodeRepository;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import com.luchkovskiy.util.EmailManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "This controller allows basic CRUD operations for Users and other functionality")
public class UserController {

    private final UserService userService;

    private final CarRentInfoService carRentInfoService;

    private final ConversionService conversionService;

    private final LocationManager locationManager;

    private final UserCardRepository userCardRepository;

    private final EmailManager emailManager;

    private final VerificationCodeRepository verificationCodeRepository;

    private final CarService carService;

    @Operation(
            summary = "Spring Data Find User By Id",
            description = "This method returns a user from the database by the given Id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "User successfully loaded",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "User doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<User> read(@PathVariable("id") @Parameter(description = "User ID in database", required = true, example = "1")
                                     @NotNull @Min(1) Long id) {
        User user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Find All Users",
            description = "This method returns an array of all users in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Users successfully loaded",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    ),
            }
    )
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Object> readAll() {
        List<User> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Create New User",
            description = "This method adds new user in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Aleksey", type = "string",
                                    description = "User name")),
                    @Parameter(name = "surname", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Luchkovskiy", type = "string",
                                    description = "User surname")),
                    @Parameter(name = "birthdayDate", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2000-05-13T16:30:00", type = "date-time",
                                    description = "User birthday date")),
                    @Parameter(name = "address", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mogilev, Pervomayskaya st.23", type = "string",
                                    description = "User address")),
                    @Parameter(name = "passportId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234567A001PB6", type = "string",
                                    description = "User passport Id")),
                    @Parameter(name = "driverId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "AA 12345678", type = "string",
                                    description = "User driver Id")),
                    @Parameter(name = "drivingExperience", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3.5", type = "number",
                                    description = "User driving experience")),
                    @Parameter(name = "accountBalance", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "120.5", type = "number",
                                    description = "User account balance")),
                    @Parameter(name = "email", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "luchkovskialexey@gmail.com", type = "string",
                                    description = "User email")),
                    @Parameter(name = "password", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "example123", type = "string",
                                    description = "User password in the system"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "User successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR", "ROLE_ANONYMOUS"})
    public ResponseEntity<User> create(@Valid @Parameter(hidden = true) @ModelAttribute UserCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        User user = conversionService.convert(request, User.class);
        User createdUser = userService.create(user);
        String code = emailManager.sendCode(createdUser);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setUser(createdUser);
        verificationCode.setCode(code);
        verificationCodeRepository.save(verificationCode);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Spring Data Update User",
            description = "This method updates an existing user and returns it from database",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Id of the user")),
                    @Parameter(name = "name", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Aleksey", type = "string",
                                    description = "User name")),
                    @Parameter(name = "surname", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Luchkovskiy", type = "string",
                                    description = "User surname")),
                    @Parameter(name = "birthdayDate", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2002-01-24T15:00:00", type = "date-time",
                                    description = "User birthday date")),
                    @Parameter(name = "active", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true", type = "boolean",
                                    description = "Is user active in the system?")),
                    @Parameter(name = "address", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mogilev, Pervomayskaya st.23", type = "string",
                                    description = "User address")),
                    @Parameter(name = "passportId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234567A001PB6", type = "string",
                                    description = "User passport Id")),
                    @Parameter(name = "driverId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "AA 12345678", type = "string",
                                    description = "User driver Id")),
                    @Parameter(name = "drivingExperience", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3.5", type = "number",
                                    description = "User driving experience")),
                    @Parameter(name = "rating", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2.2", type = "number",
                                    description = "User rating")),
                    @Parameter(name = "accountBalance", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "120.5", type = "number",
                                    description = "User account balance")),
                    @Parameter(name = "email", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "luchkovskialexey@gmail.com", type = "string",
                                    description = "User email")),
                    @Parameter(name = "password", in = ParameterIn.QUERY,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "example123", type = "string",
                                    description = "User password in the system"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "User successfully updated",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "User doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @PutMapping
    public ResponseEntity<User> update(@Valid @Parameter(hidden = true) @ModelAttribute UserUpdateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        User user = conversionService.convert(request, User.class);
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Spring Data Delete User",
            description = "This method deletes user from database by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "User deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "User doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public void delete(@PathVariable("id") @Parameter(description = "User ID in database", required = true, example = "1")
                       @Min(1) @NotNull Long id) {
        userService.delete(id);
    }
    // TODO: 21.05.2023 софт делит

    @Operation(
            summary = "Check distance to car",
            description = "This method calculates distance between user and car current locations",
            parameters = {
                    @Parameter(name = "address", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mogilev, Pervomayskaya 21", type = "string",
                                    description = "Current user address"))
            }
    )
    @GetMapping("/distance")
    public ResponseEntity<List<CarDistanceResponse>> getCarsDistance(String address) {
        Map<Long, String> carsLocations = new HashMap<>();
        List<CarDistanceResponse> responseDistances = new ArrayList<>();
        List<CarRentInfo> cars = carRentInfoService.readAll();
        for (CarRentInfo car : cars) {
            if (car.getAvailable()) {
                carsLocations.put(car.getCar().getId(), car.getCurrentLocation());
            }
        }
        for (Map.Entry<Long, String> entry : carsLocations.entrySet()) {
            responseDistances.add(new CarDistanceResponse(carService.read(entry.getKey()), locationManager.getRouteTime(
                    address, entry.getValue(), TravelMode.WALKING, "Europe/Minsk", "en-Us")));
        }
        return new ResponseEntity<>(responseDistances, HttpStatus.OK);
    }

    @Operation(
            summary = "Link payment card to user account",
            description = "This method links user account with payment card and returns linking object",
            parameters = {
                    @Parameter(name = "userId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "User Id")),
                    @Parameter(name = "cardId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Payment card Id")),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Link successfully added",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserCard.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "User doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 Not Found",
                            description = "Payment card doest not exist in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )}
    )
    @PutMapping("/card")
    public ResponseEntity<UserCard> linkPaymentCard(@Valid @Parameter(hidden = true) @ModelAttribute UserCardCreateRequest request, BindingResult bindingResult) {
        ExceptionChecker.validCheck(bindingResult);
        UserCard userCard = conversionService.convert(request, UserCard.class);
        ExceptionChecker.verifyCheck(userCard.getUser());
        UserCard savedUserCard = userCardRepository.save(userCard);
        return new ResponseEntity<>(savedUserCard, HttpStatus.OK);
    }

    @Operation(
            summary = "Unlink payment card from user account",
            description = "This method unlinks payment card from user account",
            parameters = {
                    @Parameter(name = "cardId", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer",
                                    description = "Payment card Id"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Successfully unlinked",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserCard.class)))
                    )}
    )
    @DeleteMapping("/card")
    public void unlinkPaymentCard(Principal principal, @NotNull @Min(1) Long cardId) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userCardRepository.unlinkPaymentCard(user.getId(), cardId);
    }

}

