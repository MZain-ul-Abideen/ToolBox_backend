package fr.emse.toolbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.emse.toolbox.model.Notification;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
}
