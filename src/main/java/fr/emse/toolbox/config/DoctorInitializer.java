package fr.emse.toolbox.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import fr.emse.toolbox.model.Doctor;
import fr.emse.toolbox.repository.DoctorRepository;
import fr.emse.toolbox.repository.UserRepository;
import java.util.HashSet;

@Configuration
public class DoctorInitializer {

    @Bean
    CommandLineRunner initDoctors(DoctorRepository doctorRepository, UserRepository userRepository) {
        return args -> {
            if (doctorRepository.count() == 0) {
                // Create dummy doctors
                Doctor d1 = new Doctor();
                d1.setName("Dr. John Smith");
                d1.setEmail("john.smith@hospital.com");
                d1.setSpecialization("Cardiology");
                d1.setPatients(new HashSet<>());
                doctorRepository.save(d1);

                Doctor d2 = new Doctor();
                d2.setName("Dr. Sarah Johnson");
                d2.setEmail("sarah.johnson@hospital.com");
                d2.setSpecialization("Pediatrics");
                d2.setPatients(new HashSet<>());
                doctorRepository.save(d2);

                Doctor d3 = new Doctor();
                d3.setName("Dr. Michael Chen");
                d3.setEmail("michael.chen@hospital.com");
                d3.setSpecialization("Neurology");
                d3.setPatients(new HashSet<>());
                doctorRepository.save(d3);

                // Assign the first user (if exists) to the first doctor
                userRepository.findAll().stream().findFirst().ifPresent(user -> {
                    d1.getPatients().add(user);
                    doctorRepository.save(d1);
                });
            }
        };
    }
}
