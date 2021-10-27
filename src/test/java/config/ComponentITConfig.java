package config;

import com.mongodb.MongoClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import service.UserHandler;
@Configuration
@Import({

})
@ComponentScan(basePackages = {
        "config",
        "domain",
        "repository",
        "service",
        "user"
})
@TestPropertySource("/user-service-test.properties")
public class ComponentITConfig {

    @Bean
    public UserHandler userHandler() {
        return new UserHandler();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient("localhost"), "TestGenesys");
    }
    }

