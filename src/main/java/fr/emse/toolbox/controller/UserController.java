package fr.emse.toolbox.controller;

import fr.emse.toolbox.service.UserService;
import fr.emse.toolbox.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Create a new user with all fields (including age, height, weight, bloodGroup)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);  // Service layer should handle the user creation
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);  // Service layer handles the deletion
        return ResponseEntity.noContent().build();
    }
}
