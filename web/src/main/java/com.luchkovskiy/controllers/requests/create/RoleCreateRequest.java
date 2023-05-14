package com.luchkovskiy.controllers.requests.create;

import com.luchkovskiy.models.SystemRoles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Schema(description = "Role information to save in database")
public class RoleCreateRequest {

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_USER", type = "systemRoles", description = "User's role")
    private SystemRoles systemRole;

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "User's Id in database")
    private Long userId;

}
