package uz.sngroup.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.model.event.SaleEvent;
import uz.sngroup.repository.event.InvoiceRepository;
import uz.sngroup.repository.event.SaleEventRepository;
import uz.sngroup.service.Util;
import uz.sngroup.service.report.ReportGenerator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceService {
    @Autowired InvoiceRepository invoiceRepository;
    @Autowired SaleEventRepository saleEventRepository;
    @Autowired ReportGenerator reportGenerator;
    @Autowired Util util;

    public ResponseEntity<byte[]> getReport(Long invoiceId){
        String frxmlFileName = "report" + 1;
        Invoice invoice = invoiceRepository.getById(invoiceId);
        List<SaleEvent> saleEventList = saleEventRepository.getByInvoice_Id(invoice.getId());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", util.getBufferedImageFromFolder("logo.jpg","data"));
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
        return reportGenerator.getReport(saleEventList, parameters, frxmlFileName, invoice.getNakNo());
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
            log.error("->", e);
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
