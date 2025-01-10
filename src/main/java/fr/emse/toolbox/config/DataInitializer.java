package fr.emse.toolbox.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User dummyUser = new User();
                dummyUser.setUsername("dummy_user");
                dummyUser.setEmail("dummy@example.com");
                dummyUser.setAge(25);
                dummyUser.setHeight(175.0);
                dummyUser.setWeight(70.0);
                dummyUser.setBloodGroup("O+");

                userRepository.save(dummyUser);
            }
        };
    }
}
