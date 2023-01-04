package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import con_backend.api.model.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>{
    public List<UserDevice> findByUserId(Long UserId);
}
