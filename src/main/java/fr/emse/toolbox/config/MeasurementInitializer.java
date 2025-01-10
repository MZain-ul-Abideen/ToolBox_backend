package fr.emse.toolbox.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import fr.emse.toolbox.model.Measurement;
import fr.emse.toolbox.repository.MeasurementRepository;
import fr.emse.toolbox.repository.UserRepository;
import java.time.LocalDateTime;

@Configuration
public class MeasurementInitializer {

    @Bean
    CommandLineRunner initMeasurements(MeasurementRepository measurementRepository, UserRepository userRepository) {
        return args -> {
            if (measurementRepository.count() == 0) {
                // Get the dummy user (assuming it exists from DataInitializer)
                userRepository.findAll().stream().findFirst().ifPresent(user -> {
                    // Create 5 measurements with realistic values
                    LocalDateTime baseTime = LocalDateTime.now();

                    // Measurement 1 - Normal values
                    Measurement m1 = new Measurement();
                    m1.setHeartRate(72);
                    m1.setAirQuality(85);
                    m1.setTemperature(36.6);
                    m1.setUser(user);
                    m1.setTimestamp(baseTime.minusHours(4));
                    measurementRepository.save(m1);

                    // Measurement 2 - Slightly elevated heart rate
                    Measurement m2 = new Measurement();
                    m2.setHeartRate(85);
                    m2.setAirQuality(82);
                    m2.setTemperature(36.8);
                    m2.setUser(user);
                    m2.setTimestamp(baseTime.minusHours(3));
                    measurementRepository.save(m2);

                    // Measurement 3 - Poor air quality
                    Measurement m3 = new Measurement();
                    m3.setHeartRate(75);
                    m3.setAirQuality(45);
                    m3.setTemperature(36.7);
                    m3.setUser(user);
                    m3.setTimestamp(baseTime.minusHours(2));
                    measurementRepository.save(m3);

                    // Measurement 4 - Slightly elevated temperature
                    Measurement m4 = new Measurement();
                    m4.setHeartRate(78);
                    m4.setAirQuality(88);
                    m4.setTemperature(37.2);
                    m4.setUser(user);
                    m4.setTimestamp(baseTime.minusHours(1));
                    measurementRepository.save(m4);

                    // Measurement 5 - Most recent, normal values
                    Measurement m5 = new Measurement();
                    m5.setHeartRate(70);
                    m5.setAirQuality(90);
                    m5.setTemperature(25.5);
                    m5.setUser(user);
                    m5.setTimestamp(baseTime);
                    measurementRepository.save(m5);
                });
            }
        };
    }
}
