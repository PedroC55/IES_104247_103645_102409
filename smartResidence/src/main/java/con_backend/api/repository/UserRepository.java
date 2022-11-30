package con_backend.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import con_backend.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUsername(String username);
    public User findByPassword(String password);
}