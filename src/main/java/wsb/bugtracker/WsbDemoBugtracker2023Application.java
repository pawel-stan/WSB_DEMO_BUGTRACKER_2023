package wsb.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WsbDemoBugtracker2023Application {

    public static void main(String[] args) {
        SpringApplication.run(WsbDemoBugtracker2023Application.class, args);
    }

}
