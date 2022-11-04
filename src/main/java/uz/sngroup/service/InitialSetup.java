package uz.sngroup.service;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.sngroup.model.Barcode;
import uz.sngroup.model.bys.Color;
import uz.sngroup.model.bys.Customer;
import uz.sngroup.model.bys.Gramm;
import uz.sngroup.model.bys.Quality;
import uz.sngroup.model.event.Counter;
import uz.sngroup.model.event.Event;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.sys.Role;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.bys.*;
import uz.sngroup.repository.event.EventRepository;
import uz.sngroup.repository.event.CounterRepository;
import uz.sngroup.repository.event.InvoiceRepository;
import uz.sngroup.repository.sys.UserRepository;
import uz.sngroup.service.event.BarcodeService;
import uz.sngroup.service.event.TerminalService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class InitialSetup implements ApplicationListener<ContextRefreshedEvent> {

    public void onFisrtStart(){
        addUsers();
        addCounter();
        fillObjects();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        addUsers();
//        addCounter();
//        fillObjects();
    }
    public void addCounter(){
        Counter counter = new Counter();
        counter.setSerial(100000);
        counter.setEan("47000000");
        counterRepository.save(counter);
    }
    public void addUsers() {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword(bcryptEncoder.encode("32711155"));
        admin.setRole(Role.ADMIN);

        User user1 = new User();
        user1.setLogin("sotuv");
        user1.setPassword(bcryptEncoder.encode("232567"));
        user1.setRole(Role.USER);

        User user = new User();
        user.setLogin("barkod");
        user.setPassword(bcryptEncoder.encode("123456"));
        user.setRole(Role.BARKOD);

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user1);
    }
    public void fillObjects(){
        DataFactory df = new DataFactory();
        for (int i = 0; i < 10; i++) {
            Invoice invoice = new Invoice();
            Customer customer = new Customer();
            customer.setName(df.getCity() + " " + df.getFirstName() + " " + df.getLastName());
            customer.setCode(i);
            invoice.setCustomer(customer);
            invoice.setDriverNumber(df.getRandomText(9,12));
            invoice.setCarNumber(df.getAddress());
            invoice.setCarNumber2(df.getAddress());
            invoice.setNakNo("NAK-" + df.getNumberBetween(1,31));
            invoice.setWarehouseMan(df.getName());
            customerRepository.save(customer);
            invoiceRepository.save(invoice);
        }
        for (int i = 0; i < 3; i++) {
            Quality quality = new Quality();
            Gramm gramm = new Gramm();
            Color color = new Color();
            quality.setName("quality " + df.getRandomText(8,12) + i);
            gramm.setWeight(df.getRandomText(1,2) + i);
            color.setName("color " + i);
            colorRepository.save(color);
            grammRepository.save(gramm);
            qualityRepository.save(quality);
        }
        fillBarcode();
    }
    public void fillBarcode(){
        Random r = new Random();
        DataFactory df = new DataFactory();
        qualityList = qualityRepository.findAll();
        grammList = grammRepository.findAll();
        colorList = colorRepository.findAll();
        invoiceList = invoiceRepository.findAll();
        List<Barcode> barcodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Barcode barcode = new Barcode();
            barcode.setQuality( qualityList.get(r.nextInt(qualityList.size())).getId() );
            barcode.setColor(colorList.get(r.nextInt(colorList.size())).getId());
            barcode.setGramm(grammList.get(r.nextInt(grammList.size())).getId());
            barcode.setWidth(df.getNumberBetween(180,530));
            barcode.setHeight(df.getNumberBetween(1111,3500));
//            barcode.setWeight(df.getNumberBetween(120,150));
            barcodeList.add(barcode);
        }
        for (Barcode b : barcodeList){
            barcodeService.init(b);
        }
        eventList = eventRepository.findAll();
        for (int i = 0; i < 50; i++) {
            terminalService.sell(invoiceList.get(r.nextInt(invoiceList.size())), eventList.get(r.nextInt(eventList.size())).getSerial());
        }
    }
    @Autowired private PasswordEncoder bcryptEncoder;
    @Autowired private ColorRepository colorRepository;
    @Autowired private GrammRepository grammRepository;
    @Autowired private QualityRepository qualityRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private CounterRepository counterRepository;
    @Autowired private BarcodeService barcodeService;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired private TerminalService terminalService;
    @Autowired UserRepository userRepository;
    private List<Quality> qualityList = new ArrayList<>();
    private List<Invoice> invoiceList = new ArrayList<>();
    private List<Gramm> grammList = new ArrayList<>();
    private List<Color> colorList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();

}
