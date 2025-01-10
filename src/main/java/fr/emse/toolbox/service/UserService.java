package fr.emse.toolbox.service;

import fr.emse.toolbox.repository.UserRepository;
import fr.emse.toolbox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Create a new user
    public User createUser(User user) {
        // Optional: Validation for the new fields (age, height, weight, bloodGroup)
        if (user.getAge() < 0 || user.getHeight() <= 0 || user.getWeight() <= 0) {
            throw new IllegalArgumentException("Invalid age, height, or weight");
        }
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(Long id, User user) {
        // Check if user exists
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null; // Or throw a custom exception
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
