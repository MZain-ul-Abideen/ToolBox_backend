package fr.emse.toolbox.controller;

import fr.emse.toolbox.service.MeasurementService;
import fr.emse.toolbox.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    /**
     * Get all measurements including air quality and temperature.
     * @return List of measurements
     */
    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    /**
     * Get a specific measurement by ID, including air quality and temperature.
     * @param id The ID of the measurement
     * @return ResponseEntity with measurement data or not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Measurement> getMeasurementById(@PathVariable Long id) {
        Measurement measurement = measurementService.getMeasurementById(id);
        return measurement != null ? ResponseEntity.ok(measurement) : ResponseEntity.notFound().build();
    }

    /**
     * Create a new measurement with heart rate, air quality, temperature, etc.
     * @param measurement The measurement object to create
     * @return Created measurement data
     */
    @PostMapping
    public Measurement createMeasurement(@RequestBody Measurement measurement) {
        return measurementService.createMeasurement(measurement);
    }

    /**
     * Delete a measurement by ID.
     * @param id The ID of the measurement to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable Long id) {
        measurementService.deleteMeasurement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/latest/{userId}")
    public Measurement getLatestMeasurement(@PathVariable Long userId) {
        return measurementService.findLatestByUserId(userId);
    }
}
