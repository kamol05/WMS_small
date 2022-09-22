package uz.sngroup.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.InvoiceRepository;
import uz.sngroup.service.event.EventService;
import uz.sngroup.service.event.SaleEventService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TerminalService {
    @Autowired EventRepository eventRepository;
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired SaleEventService saleEventService;
    @Autowired EventService eventService;

    public String init(Invoice invoice, Integer serial, boolean isDelete){
        try{
            if (isDelete){
                return removeFromSelled(invoice, serial);
            } else {
                return sell(invoice, serial);
            }
        }catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            return "Ichki Xatolik";
        }
    }

    public String sell(Invoice invoice, Integer serial){
        List<Event> events = eventRepository.getBySerial(serial);
        Optional<Event> in = events.stream().filter(o -> o.getEventType().equals(EventType.IN)).findFirst();
        if (!in.isPresent() || events.size() == 0){
            return "Bu Barkod topilmadi";
        }
        Optional<Event> sale = events.stream().filter(o -> o.getEventType().equals(EventType.SALE)).findFirst();
        if (sale.isPresent()){
            return "BU BARKOD SOTILGAN";
        }
        Event event = in.get();
        event = eventService.createSaleEvent(event);
        SaleEvent saleEvent = saleEventService.createNew(event, invoice);
        invoice.getEvents().add(saleEvent);
        return "BARKOD SOTILDI!!!!";
    }

    private String removeFromSelled(Invoice invoice, Integer serial) {
        String message = saleEventService.isSelled(invoice, serial);
        return message;
    }

}
