package uz.sngroup.controller.event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.service.bys.CustomerService;
import uz.sngroup.service.event.InvoiceService;


@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired InvoiceService invoiceService;
    @Autowired CustomerService customerService;

    @GetMapping(value = "/p/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> printReport(@PathVariable Long id){
        return invoiceService.getReport(id);
    }

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("invoices", invoiceService.getAll());
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("customers", customerService.getAll());
        return "models/invoice";
    }

    @PostMapping
    public String create(Invoice invoice, RedirectAttributes re){
        String message = invoiceService.save(invoice);
        re.addFlashAttribute("message", message);
        return "redirect:/invoice";
    }

    @GetMapping("/u/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("invoices", invoiceService.getAll());
        model.addAttribute("invoice", invoiceService.getById(id));
        model.addAttribute("customers", customerService.getAll());
        return "models/invoice";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes re){
        invoiceService.delete(id);
        re.addFlashAttribute("message", id + "Malumot O'chirildi!!!");
        return "redirect:/invoice";
    }
}
