package english.web.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"english.web.*"})
@EnableJpaRepositories("english.web.repositories")
@EntityScan(basePackages = {"english.web.models"})
@EnableSwagger2
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}
