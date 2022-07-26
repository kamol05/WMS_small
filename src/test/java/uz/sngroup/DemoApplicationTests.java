package uz.sngroup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.SaleEventRepository;

import static org.assertj.core.api.Assertions.*;
import java.util.*;

@SpringBootTest()
class DemoApplicationTests {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    SaleEventRepository saleEventRepository;

    @Test
    void testquery2(){
        List<SaleEvent> eventList = saleEventRepository.selectGroupByImvoiceID(11L);
        System.out.println(eventList.toString());
    }

    @Test
    void testquery(){
        List<Event> eventList = eventRepository.selectGroupByProductId();
        System.out.println(eventList.toString());
    }

    @Test
    void contextLoads() {
        Optional<Event> event = eventRepository.getBySerialAndEventType(100003, EventType.IN);
        assertThat(event).isPresent();

//        Optional<Event> event2 = eventRepository.getBySerialAndEventTypeAndEventTypeNot(100003, EventType.IN, EventType.SALE);
//        assertThat(event).isNotPresent();
    }

    @Test
    void stok(){
        Map<Long, Double[]> summap = new HashMap<>();
        List<Event> eventList = eventRepository.findAll();
        for (Event event : eventList){
            if (summap.containsKey(event.getProduct().getId())){
                Double[] sum = summap.get(event.getProduct().getId());
                if(event.getEventType().equals(EventType.IN)){
                    summap.put(event.getProduct().getId(), addToCalculated(sum, event));
                }else {
                    summap.put(event.getProduct().getId(), removeToCalculated(sum, event));
                }
            }
            else
                summap.put(event.getProduct().getId(), getCalculated(event));
        }
        for (Map.Entry<Long, Double[]> entry : summap.entrySet()) {
            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
        }

    }

     Double[] removeToCalculated(Double[] sum, Event event) {
        try {
            sum[0] = sum[0] - event.getM2();
            sum[1] = sum[1] - Double.valueOf(event.getWeight());
            sum[2] = sum[2] - Double.valueOf(event.getCount());
            sum[3] = sum[3] - Double.valueOf(event.getPieces());
            return sum;
        }catch (NullPointerException e){
            System.out.println(Arrays.toString(sum) + " " + event);
            return null;
        }
    }


     Double[] addToCalculated(Double[] sum, Event event){
        sum[0] += event.getM2();
        sum[1] += Double.valueOf(event.getWeight());
        sum[2] += Double.valueOf(event.getCount());
        sum[3] += Double.valueOf(event.getPieces());
        return sum;
    }

     Double[] getCalculated(Event event){
        Double[] sum = new Double[4];
        sum[0] = event.getM2();
        sum[1] = Double.valueOf(event.getWeight());
        sum[2] = Double.valueOf(event.getCount());
        sum[3] = Double.valueOf(event.getPieces());
        return sum;
    }

}
