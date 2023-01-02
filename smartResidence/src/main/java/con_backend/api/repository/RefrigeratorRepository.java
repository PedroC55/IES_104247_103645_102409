package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.Refrigerator;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long>{
    public Refrigerator findBySerialNumber(String serialNumber);
}
