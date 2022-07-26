package uz.sngroup.service.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.repository.bys.ProductRepository;
import uz.sngroup.repository.event.CounterRepository;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.service.bys.*;
import uz.sngroup.service.event.EventService;
import uz.sngroup.service.event.PrinterService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StockService {
    private Map<Integer, Event> map = new HashMap();
    private Map<Long, Integer> m2 = new HashMap();

    public List<Event> getGroupByProductId(){
        return eventRepository.selectGroupByProductId();
    }

    public void getAll(){
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
            } else
                summap.put(event.getProduct().getId(), getCalculated(event));
        }

        for (Map.Entry<Long, Double[]> entry : summap.entrySet()) {
            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
        }
    }

    private Double[] removeToCalculated(Double[] sum, Event event) {
        sum[0] = sum[0] - event.getM2();
        sum[1] = sum[1] - Double.valueOf(event.getWeight());
        sum[2] = sum[2] - Double.valueOf(event.getCount());
        sum[3] = sum[3] - Double.valueOf(event.getPieces());
        return sum;
    }

    private Double[] addToCalculated(Double[] sum, Event event){
        sum[0] += event.getM2();
        sum[1] += Double.valueOf(event.getWeight());
        sum[2] += Double.valueOf(event.getCount());
        sum[3] += Double.valueOf(event.getPieces());
        return sum;
    }

    private Double[] getCalculated(Event event){
        Double[] sum = new Double[4];
        sum[0] = event.getM2();
        sum[1] = Double.valueOf(event.getWeight());
        sum[2] = Double.valueOf(event.getCount());
        sum[3] = Double.valueOf(event.getPieces());
        return sum;
    }





    @Autowired private ProductRepository productRepository;
    @Autowired private CounterRepository counterRepository;
    @Autowired private PrinterService printerService;
    @Autowired private ColorService colorService;
    @Autowired private CustomerService customerService;
    @Autowired private GrammService grammService;
    @Autowired private ProductService productService;
    @Autowired private QualityService qualityService;
    @Autowired private EventService eventService;
    @Autowired private EventRepository eventRepository;

}
