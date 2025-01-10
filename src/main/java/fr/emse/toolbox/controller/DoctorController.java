package fr.emse.toolbox.controller;

import fr.emse.toolbox.model.Doctor;
import fr.emse.toolbox.model.User;
import fr.emse.toolbox.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return doctor != null ? ResponseEntity.ok(doctor) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.createDoctor(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
        return updatedDoctor != null ? ResponseEntity.ok(updatedDoctor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<Set<User>> getDoctorPatients(@PathVariable Long id) {
        Set<User> patients = doctorService.getPatients(id);
        return patients != null ? ResponseEntity.ok(patients) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{doctorId}/patients/{userId}")
    public ResponseEntity<Doctor> addPatient(@PathVariable Long doctorId, @PathVariable Long userId) {
        Doctor doctor = doctorService.addPatient(doctorId, userId);
        return doctor != null ? ResponseEntity.ok(doctor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{doctorId}/patients/{userId}")
    public ResponseEntity<Doctor> removePatient(@PathVariable Long doctorId, @PathVariable Long userId) {
        Doctor doctor = doctorService.removePatient(doctorId, userId);
        return doctor != null ? ResponseEntity.ok(doctor) : ResponseEntity.notFound().build();
    }
}
