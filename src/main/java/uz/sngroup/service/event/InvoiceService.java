package uz.sngroup.service.event;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.bys.CustomerRepository;
import uz.sngroup.repository.event.InvoiceRepository;
import uz.sngroup.repository.event.SaleEventRepository;
import uz.sngroup.service.Util;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class InvoiceService {
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired SaleEventRepository saleEventRepository;
    @Autowired Util util;

    public ResponseEntity<byte[]> getReport(Long id, int i) throws JRException {
        String reportName = "report" + i + ".jrxml";
        Invoice invoice = invoiceRepository.getById(id);
        List<SaleEvent> saleEventList = saleEventRepository.getByInvoice_Id(invoice.getId());
        JasperPrint print = generateReport(invoice, saleEventList, reportName);
        byte[] pdf = JasperExportManager.exportReportToPdf(print);
        HttpHeaders headers = new HttpHeaders();
        String nakno = invoice.getNakNo() + ".PDF";
        headers.set(nakno, nakno);
        headers.setContentDispositionFormData(nakno, nakno);
        headers.setContentType(MediaType.APPLICATION_PDF);
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    void set(){
        SaleEvent saleEvent = new SaleEvent();
        saleEvent.getProduct().getName();
        saleEvent.getProduct().getGramm().getWeight();
        saleEvent.getProduct().getColor().getName();
        // $F{student}.getStudentName()
        //
    }

    @SneakyThrows
    private JasperPrint generateReport(Invoice invoice, List<SaleEvent> list, String reportName) {
        Map<String, Object> parameters = new HashMap<>();

//        File file = ResourceUtils.getFile("classpath:logo.jpg");
//        parameters.put("logo", file.getPath());  // for developing

//        parameters.put("logo", getClass().getResource("logo.jpg").getPath()); //for production
        parameters.put("customer", invoice.getCustomer().getName());
        parameters.put("invoiceId",invoice.getId());
        parameters.put("warehouseMan", invoice.getWarehouseMan());
        parameters.put("nakNo", invoice.getNakNo());
        parameters.put("carNumber", invoice.getCarNumber());
        parameters.put("carNumber2", invoice.getCarNumber2());
        parameters.put("driverNumber", invoice.getDriverNumber());
        parameters.put("notes", invoice.getNotes());
        parameters.put("date", invoice.getDate());
        parameters.put("description", invoice.getDescription());
        JasperPrint print = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream(reportName);

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
            print = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (Exception e){
            System.out.println("Reportni ichida xato");
            e.printStackTrace();

        }
        return print;
    }

    public List<SaleEvent> getAllByInvoiceId(Long id){
        return saleEventRepository.selectGroupByImvoiceID(id);
    }


    public String save(Invoice invoice) {
        try{
            invoice.setUser(util.getLoginFromAuthentication());
            invoiceRepository.save(invoice);
            return "Malumot Kiritildi!!!";
        }catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            return "Xato!!!";
        }
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findAllByOrderByIdDesc();
    }

    public void delete(Long id){
        invoiceRepository.deleteById(id);
    }

    public Invoice getById(Long id){
        return invoiceRepository.getById(id);
    }
}
