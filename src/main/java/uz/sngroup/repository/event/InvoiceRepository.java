package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.event.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
