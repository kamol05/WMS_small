package uz.sngroup.repository.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.sys.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
