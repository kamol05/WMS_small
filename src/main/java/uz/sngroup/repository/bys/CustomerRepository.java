package uz.sngroup.repository.bys;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.bys.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
