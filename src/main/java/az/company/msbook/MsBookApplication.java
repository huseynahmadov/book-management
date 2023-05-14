package az.company.msbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MsBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsBookApplication.class, args);
    }

}
