package com.luchkovskiy;


import com.luchkovskiy.configuration.ApplicationConfig;
import com.luchkovskiy.configuration.CacheConfig;
import com.luchkovskiy.configuration.HibernateConfig;
import com.luchkovskiy.configuration.WebMVCConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.luchkovskiy")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableWebMvc
@EnableCaching
@EnableTransactionManagement
@Import(
        {ApplicationConfig.class,
                HibernateConfig.class,
                    WebMVCConfig.class,
                        CacheConfig.class}
)
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
    }

}
