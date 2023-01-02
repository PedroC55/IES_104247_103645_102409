package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.LightBulb;

@Repository
public interface LightBulbRepository extends JpaRepository<LightBulb, Long>{
    public LightBulb findBySerialNumber(String serialNumber);
}
