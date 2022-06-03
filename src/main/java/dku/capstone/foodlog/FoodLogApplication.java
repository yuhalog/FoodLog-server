package dku.capstone.foodlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;

@SpringBootApplication
public class FoodLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodLogApplication.class, args);
    }

}
