package com.luchkovskiy.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Carsharing project",
                version = "1.0",
                description = "Pet-project made for JD2 course",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Aleksey Luchkovskiy",
                        email = "luchkovskialexey@gmail.com",
                        url = "https://vk.com/luchkxvskiy"
                ),
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "MIT Licence",
                        url = "https://opensource.org/licenses/mit-license.php"
                )
        ),
        security = {
                @SecurityRequirement(name = "authToken")
        }
)
@SecuritySchemes(value = {
        @SecurityScheme(name = "authToken",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER,
                paramName = "X-Auth-Token",
                description = "X-Auth-Token for JWT Authentication"),
})
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI();
    }

}
