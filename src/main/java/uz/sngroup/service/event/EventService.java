package uz.sngroup.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.Barcode;
import uz.sngroup.model.bys.Product;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.model.event.Counter;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.CounterRepository;
import uz.sngroup.repository.event.SaleEventRepository;
import uz.sngroup.repository.sys.UserRepository;
import uz.sngroup.service.Util;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired private EventRepository eventRepository;
    @Autowired private CounterRepository counterRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SaleEventRepository saleEventRepository;
    @Autowired private Util util;

    public Event createSaleEvent(Event e){
        Event event = new Event();
        event.setProduct(e.getProduct());
        event.setEventType(EventType.SALE);
        event.setUser(e.getUser());
        event.setWidth(e.getWidth());
        event.setHeight(e.getHeight());
        event.setSerial(e.getSerial());
        event.setEan(e.getEan());
        event.setM2(e.getM2());
        event.setPieces(e.getPieces());
        event.setWeight(e.getWeight());
        eventRepository.save(event);
        return event;
    }

    public Event createInEvent(Product product, Barcode b) {
        Event event = new Event();
        event.setUser(util.getLoginFromAuthentication());
        event.setEventType(EventType.IN);
        event.setProduct(product);
        event.setEan(product.getEan());
        event.setSerial(generateSerialNumber());
        event.setWidth(b.getWidth());
        event.setHeight(b.getHeight());
        event.setPieces(b.getPieces() == null ? 0 : b.getPieces());
        event.setWeight(b.getWeight());
        event.setM2(generateM2(event));
        eventRepository.save(event);
        return event;
    }
    private Double generateM2(Event e) {
        double m2 = e.getWidth() * e.getHeight() / 10_000.0;
        if(e.getPieces() > 0){
            m2 = m2 * e.getPieces();
            m2 = Math.round(m2 * 100);
            return m2/100;
        }
        m2 = Math.round(m2 * 100);
        return m2/100;
    }

    private Integer generateSerialNumber() {
        Counter counter = counterRepository.getById(1L);
        counter.setSerial(counter.getSerial() + 1);
        counterRepository.save(counter);
        return counter.getSerial();
    }

    public List<Event> getAllBySerialNumber(Integer serial){
        return eventRepository.getBySerial(serial);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public void save(Event quality) {
        eventRepository.save(quality);
    }

    public void delete(Long id){
        eventRepository.deleteById(id);
    }

    public Event getById(Long id){
        return eventRepository.getById(id);
    }

    public String deleteBySerial(Integer serial) {
        List<Event> events = getAllBySerialNumber(serial);
        Optional<SaleEvent> optional = saleEventRepository.findBySerial(serial);
        if (optional.isPresent()){
            return "Bu Barkod Sotilgan!!";
        }
        events.forEach(o -> delete(o.getId()));
        return "BARKOD O'CHIRILDI  " + serial;
    }
}
