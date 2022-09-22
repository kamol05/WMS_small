package uz.sngroup.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.sngroup.model.Barcode;
import uz.sngroup.model.bys.*;
import uz.sngroup.model.event.Event;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.service.bys.*;
import uz.sngroup.service.bys.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BarcodeService {

    public String init(Barcode b){
        try {
            return printMultipleOrSingle(b);
        }catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            return "Ichki Xatolik !!!";
        }
    }

    private String printMultipleOrSingle(Barcode b) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        Product product = fillProduct(b);
        if (b.getQuantity() > 30){
            return "30 tadan Ko'p  barkod bosish mumkin emas";
        }
        if (b.getQuantity() > 0){
            for (int i = b.getQuantity(); i > 0 ; i--) {
                sb.append(printSingle(product, b));
                sb.append(" ");
                Thread.sleep(1000);
            }
            sb.append(" bosildi !!!!");
            return sb.toString();
        }
        sb.append(printSingle(product, b));
        sb.append(" bosildi !!!!");
        return sb.toString();
    }

    @Transactional
    public String printSingle(Product product, Barcode b){
        Event event = eventService.createInEvent(product, b);       // here making event
        printerService.print(event);                                // here sending command to label printer!
        return String.valueOf(event.getSerial());
    }

    private Product fillProduct(Barcode b) {
        Optional<Product> optional = productService.find(b);
        if (optional.isPresent()){
            return optional.get();
        }
        Product product = new Product();
        product.setColor(findColor(b.getColor()));
        product.setGramm(findGramm(b.getGramm()));
        product.setQuality(findQuality(b.getQuality()));
//        product.setName(product.getQuality().getName());
        product = productService.create(product);
        return product;

    }

    private Color findColor(Long colorId){
        return getColors().stream().filter(o -> o.getId().equals(colorId)).findFirst().get();
    }
    private Gramm findGramm(Long grammId){
        return getGramms().stream().filter(o -> o.getId().equals(grammId)).findFirst().get();
    }
    private Quality findQuality(Long qualityId){
        return getQualities().stream().filter(o -> o.getId().equals(qualityId)).findFirst().get();
    }


    List<Color> colorList = new ArrayList<>();
    List<Gramm> grammList = new ArrayList<>();
    List<Quality> qualityList = new ArrayList<>();
    public void updateLists(){
        colorList = new ArrayList<>();
        grammList = new ArrayList<>();
        qualityList = new ArrayList<>();
    }
    public List<Color> getColors(){
        if (colorList.size() > 0){
            return colorList;
        } else {
            colorList = colorService.getAll();
            return getColors();
        }
    }
    public List<Gramm> getGramms(){
        if (grammList.size() > 0){
            return grammList;
        } else {
            grammList = grammService.getAll();
            return getGramms();
        }
    }
    public List<Quality> getQualities(){
        if (qualityList.size() > 0){
            return qualityList;
        } else {
            qualityList = qualityService.getAll();
            return getQualities();
        }
    }
    @Autowired private PrinterService printerService;
    @Autowired private ColorService colorService;
    @Autowired private CustomerService customerService;
    @Autowired private GrammService grammService;
    @Autowired private ProductService productService;
    @Autowired private QualityService qualityService;
    @Autowired private EventService eventService;
    @Autowired private EventRepository eventRepository;

}
