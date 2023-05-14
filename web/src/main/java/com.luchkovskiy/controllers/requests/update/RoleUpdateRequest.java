package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
@Schema(description = "Role information to update in database")
public class RoleUpdateRequest extends RoleCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Id of the role")
    private Long id;

}
