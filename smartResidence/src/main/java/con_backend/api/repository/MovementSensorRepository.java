package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.MovementSensor;

@Repository
public interface MovementSensorRepository extends JpaRepository<MovementSensor, Long>{
    public MovementSensor findBySerialNumber(String serialNumber);
}
