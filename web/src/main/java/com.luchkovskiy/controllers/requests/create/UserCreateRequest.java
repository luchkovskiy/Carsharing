package com.luchkovskiy.controllers.requests.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "User information to save in database")
public class UserCreateRequest {

    @NotNull
    @Size(min = 2, max = 50)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Aleksey", type = "String", description = "User name")
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Luchkovskiy", type = "String", description = "User surname")
    private String surname;

    @NotNull
    @Past
    @Pattern(regexp = "^\\d{4}.\\d{2}.\\d{2}$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2002.01.24", type = "Date", description = "User birthday date")
    private Date birthdayDate;

    @NotNull
    @Size(min = 4, max = 5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "true", type = "Boolean", description = "Is user active in the system?")
    private Boolean active;

    @Size(min = 5, max = 100)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Mogilev, Pervomayskaya st.23", type = "String", description = "User address")
    private String address;

    @NotNull
    @Pattern(regexp = "^\\d{7}[A-Z]\\d{3}[A-Z]{2}\\d$")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1234567A001PB6", type = "String", description = "User passport Id")
    private String passportId;

    @NotNull
    @Pattern(regexp = "^[A-D]{2} \\d{8}")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "AA 12345678", type = "String", description = "User driver Id")
    private String driverId;

    @NotNull
    @Min(2)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "3.5", type = "Float", description = "User driving experience")
    private Float drivingExperience;

    @NotNull
    @Min(1)
    @Max(5)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2.2", type = "Float", description = "User rating")
    private Float rating;

    @Min(0)
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "120.5", type = "Float", description = "User account balance")
    private Float accountBalance;

    @Email
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "luchkovskialexey@gmail.com", type = "String", description = "User email")
    private String email;

    @NotNull
    @Size(min = 5, max = 25)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "example123", type = "String", description = "User password in the system")
    private String password;

}
