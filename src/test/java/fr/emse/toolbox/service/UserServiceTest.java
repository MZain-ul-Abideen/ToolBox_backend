package fr.emse.toolbox.service;

import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        expectedUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUserById(id);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_ShouldReturnNull_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        User actualUser = userService.getUserById(id);

        // Assert
        assertNull(actualUser);
        verify(userRepository).findById(id);
    }

    @Test
    void createUser_ShouldSaveAndReturnUser() {
        // Arrange
        User user = new User();
        user.setAge(25);
        user.setHeight(175.0);
        user.setWeight(70.0);
        user.setBloodGroup("A+");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.createUser(user);

        // Assert
        assertNotNull(savedUser);
        verify(userRepository).save(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenInvalidData() {
        // Arrange
        User user = new User();
        user.setAge(-1);
        user.setHeight(-175.0);
        user.setWeight(-70.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUser() {
        // Arrange
        Long id = 1L;
        User user = new User();
        user.setAge(30);
        user.setHeight(180.0);
        user.setWeight(75.0);

        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User updatedUser = userService.updateUser(id, user);

        // Assert
        assertNotNull(updatedUser);
        verify(userRepository).existsById(id);
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_ShouldReturnNull_WhenUserNotFound() {
        // Arrange
        Long id = 1L;
        User user = new User();

        when(userRepository.existsById(id)).thenReturn(false);

        // Act
        User updatedUser = userService.updateUser(id, user);

        // Assert
        assertNull(updatedUser);
        verify(userRepository).existsById(id);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        // Arrange
        Long id = 1L;

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository).deleteById(id);
    }
}
