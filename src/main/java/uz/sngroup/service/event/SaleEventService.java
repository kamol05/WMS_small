package uz.sngroup.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.SaleEventRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SaleEventService {
    @Autowired SaleEventRepository saleEventRepository;
    @Autowired EventRepository eventRepository;
    @Autowired EventService eventService;

    public SaleEvent createNew(Event event, Invoice invoice){
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.setSerial(event.getSerial());
        saleEvent.setEan(event.getEan());
        saleEvent.setWidth(event.getWidth());
        saleEvent.setHeight(event.getHeight());
        saleEvent.setM2(event.getM2());
        saleEvent.setCount(event.getCount());
        saleEvent.setPieces(event.getPieces());
        saleEvent.setWeight(event.getWeight());
        saleEvent.setDescription(event.getDescription());
        saleEvent.setNotes(event.getNotes());
        saleEvent.setProduct(event.getProduct());
        saleEvent.setInvoice(invoice);
        saleEvent.setCustomer(invoice.getCustomer());
        saleEventRepository.save(saleEvent);
        return saleEvent;
    }

    public List<SaleEvent> getAll() {
        return saleEventRepository.findAll();
    }

    public void save(SaleEvent saleEvent) {
        saleEventRepository.save(saleEvent);
    }

    public void delete(Long id){
        saleEventRepository.deleteById(id);
    }

    public SaleEvent getById(Long id){
        return saleEventRepository.getById(id);
    }

    public String isSelled(Invoice invoice, Integer serial) {
        Optional<SaleEvent> optionalSaleEvent = saleEventRepository.getByInvoiceAndSerial(invoice, serial);
        if (optionalSaleEvent.isPresent()){
            Optional<Event> optionalEvent = eventRepository.getBySerialAndEventType(serial, EventType.SALE);
            if (optionalEvent.isPresent()){
                deleteAllFromSale(optionalSaleEvent.get().getId(), optionalEvent.get().getId());
                return serial + "  O'CHIRILDI";
            } else return "Xatolik 1";

        } else return "BOSHQA KLENTGA SOTILGAN";
    }

    @Transactional
    public void deleteAllFromSale(Long saleEventId, Long eventId){
        delete(saleEventId);
        eventService.delete(eventId);
    }
}
