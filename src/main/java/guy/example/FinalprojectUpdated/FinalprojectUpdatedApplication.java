package guy.example.FinalprojectUpdated;

import guy.example.FinalprojectUpdated.config.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
        ( basePackageClasses= {RSAKeyProperties.class})
public class FinalprojectUpdatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalprojectUpdatedApplication.class, args);
    }

}
