package ch.bbw.pwd_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PwdManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PwdManagerApplication.class, args);
    }

}
