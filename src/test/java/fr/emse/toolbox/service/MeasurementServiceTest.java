package fr.emse.toolbox.service;

import fr.emse.toolbox.model.Measurement;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.MeasurementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Measurement Service Tests")
public class MeasurementServiceTest {

    @MockBean
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementService measurementService;

    @Test
    @DisplayName("Should return all measurements")
    public void getAllMeasurements_ShouldReturnAllMeasurements() {
        // Arrange
        Measurement measurement1 = new Measurement();
        measurement1.setId(1L);
        Measurement measurement2 = new Measurement();
        measurement2.setId(2L);
        List<Measurement> expectedMeasurements = Arrays.asList(measurement1, measurement2);

        when(measurementRepository.findAll()).thenReturn(expectedMeasurements);

        // Act
        List<Measurement> actualMeasurements = measurementService.getAllMeasurements();

        // Assert
        assertEquals(expectedMeasurements, actualMeasurements);
        verify(measurementRepository).findAll();
    }

    @Test
    @DisplayName("Should return measurement by ID when exists")
    public void getMeasurementById_ShouldReturnMeasurement() {
        // Arrange
        Long id = 1L;
        Measurement expectedMeasurement = new Measurement();
        expectedMeasurement.setId(id);

        when(measurementRepository.findById(id)).thenReturn(Optional.of(expectedMeasurement));

        // Act
        Measurement actualMeasurement = measurementService.getMeasurementById(id);

        // Assert
        assertEquals(expectedMeasurement, actualMeasurement);
        verify(measurementRepository).findById(id);
    }

    @Test
    @DisplayName("Should return null when measurement ID not found")
    public void getMeasurementById_ShouldReturnNull_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(measurementRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Measurement actualMeasurement = measurementService.getMeasurementById(id);

        // Assert
        assertNull(actualMeasurement);
        verify(measurementRepository).findById(id);
    }

    @Test
    @DisplayName("Should set timestamp and save measurement")
    public void createMeasurement_ShouldSetTimestampAndSave() {
        // Arrange
        Measurement measurement = new Measurement();
        measurement.setHeartRate(75);
        measurement.setAirQuality(95);
        measurement.setTemperature(36.5);

        User user = new User();
        user.setId(1L);
        measurement.setUser(user);

        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);

        // Act
        Measurement savedMeasurement = measurementService.createMeasurement(measurement);

        // Assert
        assertNotNull(savedMeasurement);
        assertNotNull(savedMeasurement.getTimestamp());
        assertEquals(75, savedMeasurement.getHeartRate());
        assertEquals(95, savedMeasurement.getAirQuality());
        assertEquals(36.5, savedMeasurement.getTemperature());
        verify(measurementRepository).save(measurement);
    }

    @Test
    @DisplayName("Should delete measurement")
    public void deleteMeasurement_ShouldCallRepository() {
        // Arrange
        Long id = 1L;

        // Act
        measurementService.deleteMeasurement(id);

        // Assert
        verify(measurementRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should return latest measurement for user")
    public void findLatestByUserId_ShouldReturnLatestMeasurement() {
        // Arrange
        Long userId = 1L;
        Measurement latestMeasurement = new Measurement();
        latestMeasurement.setId(1L);
        latestMeasurement.setTimestamp(LocalDateTime.now());

        when(measurementRepository.findLatestByUserId(userId))
            .thenReturn(Arrays.asList(latestMeasurement));

        // Act
        Measurement result = measurementService.findLatestByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(latestMeasurement, result);
        verify(measurementRepository).findLatestByUserId(userId);
    }

    @Test
    @DisplayName("Should return null when no measurements found for user")
    public void findLatestByUserId_ShouldReturnNull_WhenNoMeasurementsFound() {
        // Arrange
        Long userId = 1L;
        when(measurementRepository.findLatestByUserId(userId))
            .thenReturn(Collections.emptyList());

        // Act
        Measurement result = measurementService.findLatestByUserId(userId);

        // Assert
        assertNull(result);
        verify(measurementRepository).findLatestByUserId(userId);
    }
}
