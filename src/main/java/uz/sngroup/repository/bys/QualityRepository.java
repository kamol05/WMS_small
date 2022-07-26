package uz.sngroup.repository.bys;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.bys.Quality;

import java.util.Optional;

public interface QualityRepository extends JpaRepository<Quality, Long> {
    Optional<Quality> findByName(String name);
}
