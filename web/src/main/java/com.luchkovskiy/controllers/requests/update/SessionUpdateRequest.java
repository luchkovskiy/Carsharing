package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
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
@Schema(description = "Session information to update in database")
public class SessionUpdateRequest extends SessionCreateRequest {

    @NotNull
    @Min(1)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1", type = "integer", description = "Id of the session")
    private Long id;

}
