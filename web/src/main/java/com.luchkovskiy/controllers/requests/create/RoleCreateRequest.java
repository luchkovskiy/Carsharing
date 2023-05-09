package com.luchkovskiy.controllers.requests.create;

import com.luchkovskiy.models.SystemRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleCreateRequest {

    private SystemRoles systemRole;

    private Long userId;


}
