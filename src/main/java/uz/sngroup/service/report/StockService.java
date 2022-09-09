package uz.sngroup.service.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.EventType;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.bys.ProductRepository;
import uz.sngroup.repository.event.CounterRepository;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.SaleEventRepository;
import uz.sngroup.service.Util;
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
    @Autowired Util util;
    @Autowired ReportGenerator reportGenerator;
    @Autowired private EventRepository eventRepository;
    @Autowired private SaleEventRepository saleventRepository;

    public ResponseEntity<byte[]> getStockPdfReport() {
        String frxmlName = "stock";
        String reportName = "Остаток на Складе";
        List<Event> list = getGroupByProductId();
        Map<String, Object> parameters = new HashMap<>();
        return reportGenerator.getReport(list, parameters, frxmlName, reportName);
    }

    public List<Event> getGroupByProductId(){
        return eventRepository.selectGroupByProductId();
    }

    public List<Event> getAllByProductId(Long id){
        return eventRepository.getAllByProductIDSpecial(id);
    }

    public List<Event> findAllInStock() {
        return eventRepository.findAllInStock();
    }
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<SaleEvent> findAllSelled() {
        return saleventRepository.findAll();
    }
}
