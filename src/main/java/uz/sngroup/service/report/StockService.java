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

    public List<Event> getGroupByProductId(){
        return eventRepository.selectGroupByProductId();
    }

    public List<Event> getAllByProductId(Long id){
        return eventRepository.getAllByProductIDSpecial(id);
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
