package fr.emse.toolbox.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import fr.emse.toolbox.model.Notification;
import fr.emse.toolbox.repository.NotificationRepository;
import fr.emse.toolbox.repository.UserRepository;
import java.time.LocalDateTime;

@Configuration
public class NotificationInitializer {

    @Bean
    @DependsOn({"initDatabase", "initDoctors"})
    CommandLineRunner initNotifications(NotificationRepository notificationRepository, UserRepository userRepository) {
        return args -> {
            if (notificationRepository.count() == 0) {
                // Get user with ID 1
                userRepository.findById(1L).ifPresent(user -> {
                    System.out.println("Creating notifications for user: " + user.getUsername());

                    // Create 5 notifications with different timestamps
                    LocalDateTime baseTime = LocalDateTime.now();

                    // Notification 1 - Most recent
                    Notification n1 = new Notification();
                    n1.setText("High heart rate detected: 120 BPM");
                    n1.setUser(user);
                    n1.setTimestamp(baseTime);
                    n1.setIsUserFine(true);
                    notificationRepository.save(n1);

                    // Notification 2 - 1 hour ago
                    Notification n2 = new Notification();
                    n2.setText("Air quality is poor: 45%");
                    n2.setUser(user);
                    n2.setTimestamp(baseTime.minusHours(1));
                    n2.setIsUserFine(false);
                    notificationRepository.save(n2);

                    // Notification 3 - 2 hours ago
                    Notification n3 = new Notification();
                    n3.setText("Temperature is high: 38.5°C");
                    n3.setUser(user);
                    n3.setTimestamp(baseTime.minusHours(2));
                    n3.setIsUserFine(false);
                    notificationRepository.save(n3);

                    // Notification 4 - 1 day ago
                    Notification n4 = new Notification();
                    n4.setText("Heart rate normalized: 75 BPM");
                    n4.setUser(user);
                    n4.setTimestamp(baseTime.minusDays(1));
                    n4.setIsUserFine(true);
                    notificationRepository.save(n4);

                    // Notification 5 - 2 days ago
                    Notification n5 = new Notification();
                    n5.setText("Weekly health report: All parameters normal");
                    n5.setUser(user);
                    n5.setTimestamp(baseTime.minusDays(2));
                    notificationRepository.save(n5);

                    System.out.println("Created 5 notifications for user: " + user.getUsername());
                });
            }
        };
    }
}
