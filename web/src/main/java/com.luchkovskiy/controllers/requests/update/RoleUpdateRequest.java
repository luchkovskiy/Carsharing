package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Validated
public class RoleUpdateRequest extends RoleCreateRequest {

    @NotEmpty
    @Min(1)
    private Long id;

}
