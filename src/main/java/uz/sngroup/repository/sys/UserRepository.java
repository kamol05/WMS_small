package uz.sngroup.repository.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.sys.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
