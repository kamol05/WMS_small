package uz.sngroup.controller.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.service.event.SellingService;
import uz.sngroup.service.event.InvoiceService;

@Controller
@RequestMapping("/")
public class SellingController {
    @Autowired SellingService sellingService;
    @Autowired InvoiceService invoiceService;

    @GetMapping("selling")
    public String getForm(Model model){
        model.addAttribute("invoices", invoiceService.getAll());
        model.addAttribute("dto", new Dto());
        return "barcode/selling";
    }
    @GetMapping("serial")
    public String getSerialReaderForm(Dto dto, Model model, RedirectAttributes re){
//        System.out.println(dto.getSerial());
        if (dto.getInvoice() == null){
            re.addFlashAttribute("error", "Nakladnoy nomer kiritlmagan");
            return "redirect:/selling";
        }
        model.addAttribute("dto", dto);
        return "barcode/serialreader";
    }
    @PostMapping("read")
    public String putReadSerial(Dto dto, Model model, RedirectAttributes re){
//        System.out.println(dto.getSerial() + " " +  dto.getInvoice());
        if (dto.getInvoice() == null){
            re.addFlashAttribute("dto", dto);
            re.addFlashAttribute("error", "Nakladnoy nomer kiritlmagan");
            return "redirect:/selling";
        }
        if (dto.getSerial() == null){
            model.addAttribute("dto", dto);
            model.addAttribute("message", "Seriya yuq");
            return "barcode/serialreader";
        }
        String message = sellingService.init(dto.getInvoice(), dto.getSerial(), dto.isDelete());
        model.addAttribute("dto", dto);
        model.addAttribute("message", message);
        return "barcode/serialreader";
    }


    @GetMapping("read") // if make get request
    public String toSelling(RedirectAttributes re){
        re.addFlashAttribute("error", "XATOLIK!");
        return "redirect:/selling";
    }







    @PostMapping("/b/")
    public String sell(){
        return "redirect:/selling";
    }

    @GetMapping("/t")
    public String getTerminalForm(){
        return "barcode/terminal";
    }

    @Getter
    @Setter
    @ToString
    private static class Dto{
        private Integer serial;
        Invoice invoice;
        private boolean delete;
    }
}
