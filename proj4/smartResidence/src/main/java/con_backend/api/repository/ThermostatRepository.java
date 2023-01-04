package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.Thermostat;

@Repository
public interface ThermostatRepository extends JpaRepository<Thermostat, Long>{
    public Thermostat findBySerialNumber(String serialNumber);
}
