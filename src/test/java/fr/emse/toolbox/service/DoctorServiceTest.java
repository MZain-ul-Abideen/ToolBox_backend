package fr.emse.toolbox.service;

import fr.emse.toolbox.model.Doctor;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.DoctorRepository;
import fr.emse.toolbox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctors_ShouldReturnAllDoctors() {
        // Arrange
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        Doctor doctor2 = new Doctor();
        doctor2.setId(2L);
        List<Doctor> expectedDoctors = Arrays.asList(doctor1, doctor2);

        when(doctorRepository.findAll()).thenReturn(expectedDoctors);

        // Act
        List<Doctor> actualDoctors = doctorService.getAllDoctors();

        // Assert
        assertEquals(expectedDoctors, actualDoctors);
        verify(doctorRepository).findAll();
    }

    @Test
    void getDoctorById_ShouldReturnDoctor() {
        // Arrange
        Long id = 1L;
        Doctor expectedDoctor = new Doctor();
        expectedDoctor.setId(id);

        when(doctorRepository.findById(id)).thenReturn(Optional.of(expectedDoctor));

        // Act
        Doctor actualDoctor = doctorService.getDoctorById(id);

        // Assert
        assertEquals(expectedDoctor, actualDoctor);
        verify(doctorRepository).findById(id);
    }

    @Test
    void getDoctorById_ShouldReturnNull_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Doctor actualDoctor = doctorService.getDoctorById(id);

        // Assert
        assertNull(actualDoctor);
        verify(doctorRepository).findById(id);
    }

    @Test
    void createDoctor_ShouldSaveAndReturnDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Smith");
        doctor.setEmail("smith@hospital.com");
        doctor.setSpecialization("Cardiology");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act
        Doctor savedDoctor = doctorService.createDoctor(doctor);

        // Assert
        assertNotNull(savedDoctor);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void updateDoctor_ShouldUpdateAndReturnDoctor() {
        // Arrange
        Long id = 1L;
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(id);
        existingDoctor.setName("Dr. Smith");
        existingDoctor.setEmail("smith@hospital.com");
        existingDoctor.setSpecialization("Cardiology");

        Doctor updatedDetails = new Doctor();
        updatedDetails.setName("Dr. Smith Jr.");
        updatedDetails.setEmail("smithjr@hospital.com");
        updatedDetails.setSpecialization("Neurology");

        when(doctorRepository.findById(id)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(existingDoctor);

        // Act
        Doctor updatedDoctor = doctorService.updateDoctor(id, updatedDetails);

        // Assert
        assertNotNull(updatedDoctor);
        assertEquals(updatedDetails.getName(), existingDoctor.getName());
        assertEquals(updatedDetails.getEmail(), existingDoctor.getEmail());
        assertEquals(updatedDetails.getSpecialization(), existingDoctor.getSpecialization());
        verify(doctorRepository).findById(id);
        verify(doctorRepository).save(existingDoctor);
    }

    @Test
    void updateDoctor_ShouldReturnNull_WhenDoctorNotFound() {
        // Arrange
        Long id = 1L;
        Doctor updatedDetails = new Doctor();

        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Doctor updatedDoctor = doctorService.updateDoctor(id, updatedDetails);

        // Assert
        assertNull(updatedDoctor);
        verify(doctorRepository).findById(id);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void deleteDoctor_ShouldCallRepository() {
        // Arrange
        Long id = 1L;

        // Act
        doctorService.deleteDoctor(id);

        // Assert
        verify(doctorRepository).deleteById(id);
    }

    @Test
    void addPatient_ShouldAddPatientToDoctor() {
        // Arrange
        Long doctorId = 1L;
        Long userId = 1L;

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setPatients(new HashSet<>());

        User user = new User();
        user.setId(userId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act
        Doctor updatedDoctor = doctorService.addPatient(doctorId, userId);

        // Assert
        assertNotNull(updatedDoctor);
        assertTrue(updatedDoctor.getPatients().contains(user));
        verify(doctorRepository).findById(doctorId);
        verify(userRepository).findById(userId);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void addPatient_ShouldReturnNull_WhenDoctorNotFound() {
        // Arrange
        Long doctorId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Doctor result = doctorService.addPatient(doctorId, userId);

        // Assert
        assertNull(result);
        verify(doctorRepository).findById(doctorId);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }

    @Test
    void removePatient_ShouldRemovePatientFromDoctor() {
        // Arrange
        Long doctorId = 1L;
        Long userId = 1L;

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        User user = new User();
        user.setId(userId);

        Set<User> patients = new HashSet<>();
        patients.add(user);
        doctor.setPatients(patients);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act
        Doctor updatedDoctor = doctorService.removePatient(doctorId, userId);

        // Assert
        assertNotNull(updatedDoctor);
        assertFalse(updatedDoctor.getPatients().contains(user));
        verify(doctorRepository).findById(doctorId);
        verify(userRepository).findById(userId);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void getPatients_ShouldReturnPatients() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        Set<User> patients = new HashSet<>();
        User user = new User();
        user.setId(1L);
        patients.add(user);
        doctor.setPatients(patients);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        Set<User> result = doctorService.getPatients(doctorId);

        // Assert
        assertNotNull(result);
        assertEquals(patients, result);
        verify(doctorRepository).findById(doctorId);
    }

    @Test
    void getPatients_ShouldReturnNull_WhenDoctorNotFound() {
        // Arrange
        Long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act
        Set<User> result = doctorService.getPatients(doctorId);

        // Assert
        assertNull(result);
        verify(doctorRepository).findById(doctorId);
    }
}
