package fr.emse.toolbox.service;

import fr.emse.toolbox.model.Doctor;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.repository.DoctorRepository;
import fr.emse.toolbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorById(id);
        if (doctor != null) {
            doctor.setName(doctorDetails.getName());
            doctor.setEmail(doctorDetails.getEmail());
            doctor.setSpecialization(doctorDetails.getSpecialization());
            return doctorRepository.save(doctor);
        }
        return null;
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public Doctor addPatient(Long doctorId, Long userId) {
        Doctor doctor = getDoctorById(doctorId);
        User user = userRepository.findById(userId).orElse(null);

        if (doctor != null && user != null) {
            Set<User> patients = doctor.getPatients();
            patients.add(user);
            doctor.setPatients(patients);
            return doctorRepository.save(doctor);
        }
        return null;
    }

    @Transactional
    public Doctor removePatient(Long doctorId, Long userId) {
        Doctor doctor = getDoctorById(doctorId);
        User user = userRepository.findById(userId).orElse(null);

        if (doctor != null && user != null) {
            Set<User> patients = doctor.getPatients();
            patients.remove(user);
            doctor.setPatients(patients);
            return doctorRepository.save(doctor);
        }
        return null;
    }

    public Set<User> getPatients(Long doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        return doctor != null ? doctor.getPatients() : null;
    }
}
