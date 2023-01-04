package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>{
    public Device findByName(String username);

}