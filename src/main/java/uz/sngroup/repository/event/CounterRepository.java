package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.event.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {
}
