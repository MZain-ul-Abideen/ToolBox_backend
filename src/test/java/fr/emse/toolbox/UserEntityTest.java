package fr.emse.toolbox;

import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUserTableCreation() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }
}
