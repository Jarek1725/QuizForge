package tomaszewski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = {"tomaszewski"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}