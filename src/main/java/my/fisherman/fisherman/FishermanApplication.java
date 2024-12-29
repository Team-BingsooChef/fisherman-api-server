package my.fisherman.fisherman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FishermanApplication {

    public static void main(String[] args) {
        SpringApplication.run(FishermanApplication.class, args);
    }

}
