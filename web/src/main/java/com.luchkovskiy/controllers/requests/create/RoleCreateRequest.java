package com.luchkovskiy.controllers.requests.create;

import com.luchkovskiy.models.SystemRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class RoleCreateRequest {

    @NotEmpty
    private SystemRoles systemRole;

    @NotEmpty
    private Long userId;

}
