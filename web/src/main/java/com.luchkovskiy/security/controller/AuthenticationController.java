package com.luchkovskiy.security.controller;

import com.luchkovskiy.controllers.exceptions.ErrorMessage;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.VerificationCode;
import com.luchkovskiy.repository.VerificationCodeRepository;
import com.luchkovskiy.security.config.JWTConfiguration;
import com.luchkovskiy.security.dto.AuthRequest;
import com.luchkovskiy.security.dto.AuthResponse;
import com.luchkovskiy.security.jwt.TokenProvider;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.util.SpringSecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
@Tag(name = "Security controller", description = "This controller allows to login and logout user from the system")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider provider;

    private final UserDetailsService userProvider;

    private final UserService userService;

    private final JWTConfiguration configuration;

    private final SpringSecurityUtils securityUtils;

    private final VerificationCodeRepository verificationCodeRepository;


    @Operation(
            summary = "Login",
            description = "This method adds new subscription level in database and returns it with generated ID",
            parameters = {
                    @Parameter(name = "login", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "admin@admin.com", type = "string",
                                    description = "User email in the system")),
                    @Parameter(name = "password", in = ParameterIn.QUERY, required = true,
                            schema = @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "admin123", type = "string",
                                    description = "User password in the system"))
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Login successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404 OK",
                            description = "Bad credentials",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> login(@ModelAttribute @Parameter(hidden = true) AuthRequest request) {

        /*Check login and password*/
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword() + configuration.getServerPasswordSalt()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        /*Generate token with answer to user*/
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(request.getLogin())
                        .token(
                                provider.generateToken(
                                        userProvider.loadUserByUsername(request.getLogin())
                                )
                        )
                        .build()
        );
    }

    @Operation(
            summary = "Logout",
            description = "This method clears the Spring context from the user data",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Successfully logout"
                    )
            }
    )
    @DeleteMapping("/logout")
    public void logout(@Parameter(hidden = true) Principal principal) {
        securityUtils.logout(principal);
    }

    @Operation(
            summary = "Verify email",
            description = "This method compares the code sent to the email with the entered one and activates the account if it matches",
            responses = {
                    @ApiResponse(
                            responseCode = "200 OK",
                            description = "Account verified"
                    )
            }
    )
    @PatchMapping("/verify")
    public void verifyEmail(Principal principal, String code) {
        User user = userService.findByEmail(principal.getName()).orElseThrow(RuntimeException::new);
        VerificationCode verificationCode = verificationCodeRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);
        if (verificationCode.getCode().equals(code.toUpperCase())) {
            user.setActive(true);
            verificationCodeRepository.delete(verificationCode);
            userService.update(user);
        } else
            throw new RuntimeException("Codes don't match, please try again");
    }
}
