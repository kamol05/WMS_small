package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;

import java.util.List;
import java.util.Optional;

public interface SaleEventRepository extends JpaRepository<SaleEvent, Long> {

    Optional<SaleEvent> getByInvoiceAndSerial(Invoice invoice, Integer serial);

    List<SaleEvent> getByInvoice_Id(Long invoiceId);
}
