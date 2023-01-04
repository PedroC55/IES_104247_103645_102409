package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.Vacuum;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long>{
    public Vacuum findBySerialNumber(String serialNumber);
}
