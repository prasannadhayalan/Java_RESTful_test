package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import service.UserHandler;
@Configuration
public class AdminContext {
    @Bean
    public UserHandler userHandler() {
        return new UserHandler();
    }
}
