package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.event.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByOrderByIdDesc();
}
