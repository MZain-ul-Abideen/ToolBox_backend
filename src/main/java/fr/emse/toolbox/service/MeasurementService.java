package fr.emse.toolbox.service;

import fr.emse.toolbox.repository.MeasurementRepository;
import fr.emse.toolbox.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public Measurement getMeasurementById(Long id) {
        return measurementRepository.findById(id).orElse(null);
    }

    public Measurement createMeasurement(Measurement measurement) {
        // Ensure the timestamp is set when creating a new measurement
        measurement.setTimestamp(LocalDateTime.now());

        // The airQuality and temperature fields are automatically handled since they are part of the model
        return measurementRepository.save(measurement);
    }

    public void deleteMeasurement(Long id) {
        measurementRepository.deleteById(id);
    }

    public Measurement findLatestByUserId(Long userId) {
        List<Measurement> measurements = measurementRepository.findLatestByUserId(userId);
        return measurements.isEmpty() ? null : measurements.get(0);
    }
}
