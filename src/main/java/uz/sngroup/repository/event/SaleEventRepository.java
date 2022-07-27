package uz.sngroup.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;

import java.util.List;
import java.util.Optional;

public interface SaleEventRepository extends JpaRepository<SaleEvent, Long> {

    Optional<SaleEvent> findBySerial(Integer serial);

    Optional<SaleEvent> getByInvoiceAndSerial(Invoice invoice, Integer serial);

    List<SaleEvent> getByInvoice_Id(Long invoiceId);

    @Query(value = "select\n" +
            "    product_id as id,\n" +
            "    sum( m2) as m2,\n" +
            "    count(*) as count,\n" +
            "    sum(weight) as weight,\n" +
            "    sum(pieces) as pieces,\n" +
            "    clock_timestamp () as date,\n" +
            "    clock_timestamp () as modified,\n" +
            "    avg(1) as serial,\n" +
            "    avg(2) as ean,\n" +
            "    avg(3) as width,\n" +
            "    sum(height) as height,\n" +
            "    avg(5) as description,\n" +
            "    avg(7) as notes,\n" +
            "    avg(1) as user_id,\n" +
            "    'SALE' as event_type, " +
            "    avg(1) as customer_id, " +
            "    avg(1) as invoice_id, " +
            "    product_id as product_id " +
            "from saleevents\n" +
            "where invoice_id = ?1 " +
            "group by product_id " +
            "order by id asc ",
            nativeQuery = true )
    List<SaleEvent> selectGroupByImvoiceID(Long id);
}
