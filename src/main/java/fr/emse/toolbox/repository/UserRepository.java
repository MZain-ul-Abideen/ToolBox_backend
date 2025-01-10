package fr.emse.toolbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.emse.toolbox.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
