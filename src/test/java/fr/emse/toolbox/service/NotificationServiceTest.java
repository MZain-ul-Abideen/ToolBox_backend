package fr.emse.toolbox.service;

import fr.emse.toolbox.model.Notification;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotifications_ShouldReturnAllNotifications() {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setId(1L);
        Notification notification2 = new Notification();
        notification2.setId(2L);
        List<Notification> expectedNotifications = Arrays.asList(notification1, notification2);

        when(notificationRepository.findAll()).thenReturn(expectedNotifications);

        // Act
        List<Notification> actualNotifications = notificationService.getAllNotifications();

        // Assert
        assertEquals(expectedNotifications, actualNotifications);
        verify(notificationRepository).findAll();
    }

    @Test
    void getNotificationsByUserId_ShouldReturnUserNotifications() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Notification notification = new Notification();
        notification.setUser(user);
        List<Notification> expectedNotifications = Arrays.asList(notification);

        when(notificationRepository.findByUserIdOrderByTimestampDesc(userId))
            .thenReturn(expectedNotifications);

        // Act
        List<Notification> actualNotifications = notificationService.getNotificationsByUserId(userId);

        // Assert
        assertEquals(expectedNotifications, actualNotifications);
        verify(notificationRepository).findByUserIdOrderByTimestampDesc(userId);
    }

    @Test
    void getNotificationById_ShouldReturnNotification() {
        // Arrange
        Long id = 1L;
        Notification expectedNotification = new Notification();
        expectedNotification.setId(id);

        when(notificationRepository.findById(id)).thenReturn(Optional.of(expectedNotification));

        // Act
        Notification actualNotification = notificationService.getNotificationById(id);

        // Assert
        assertEquals(expectedNotification, actualNotification);
        verify(notificationRepository).findById(id);
    }

    @Test
    void getNotificationById_ShouldReturnNull_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Notification actualNotification = notificationService.getNotificationById(id);

        // Assert
        assertNull(actualNotification);
        verify(notificationRepository).findById(id);
    }

    @Test
    void createNotification_ShouldSetTimestampAndSave() {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1L);

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        Notification savedNotification = notificationService.createNotification(notification);

        // Assert
        assertNotNull(savedNotification);
        assertNotNull(notification.getTimestamp());
        verify(notificationRepository).save(notification);
    }

    @Test
    void deleteNotification_ShouldCallRepository() {
        // Arrange
        Long id = 1L;

        // Act
        notificationService.deleteNotification(id);

        // Assert
        verify(notificationRepository).deleteById(id);
    }
}
