package com.luchkovskiy.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .contact(contact())
                .title("Carsharing project")
                .description("Pet-project made for JD2 course")
                .version("1.0")
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("MIT Licence")
                .url("https://opensource.org/licenses/mit-license.php");
    }

    private Contact contact() {
        return new Contact()
                .email("luchkovskialexey@gmail.com")
                .name("Luchkovskiy Aleksey")
                .url("https://vk.com/luchkxvskiy");
    }

}
