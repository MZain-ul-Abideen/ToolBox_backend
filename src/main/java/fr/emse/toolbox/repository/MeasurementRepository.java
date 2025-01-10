package fr.emse.toolbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fr.emse.toolbox.model.Measurement;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    @Query("SELECT m FROM Measurement m WHERE m.user.id = :userId ORDER BY m.timestamp DESC")
    List<Measurement> findLatestByUserId(@Param("userId") Long userId);
}
