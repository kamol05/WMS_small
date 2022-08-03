package uz.sngroup.controller.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.event.Invoice;
import uz.sngroup.service.event.InvoiceService;
import uz.sngroup.service.event.TerminalService;

@Controller
@RequestMapping("/terminal")
public class TerminalController {
    @Autowired
    TerminalService terminalService;
    @Autowired InvoiceService invoiceService;

    @GetMapping()
    public String getForm(Model model){
        model.addAttribute("invoices", invoiceService.getAll());
        model.addAttribute("dto", new Dto());
        return "barcode/terminal1";
    }

    @GetMapping("/2")
    public String getSerialReaderForm(Dto dto, Model model, RedirectAttributes re){
        if (dto.getInvoice() == null){
            re.addFlashAttribute("error", "Nakladnoy nomer kiritlmagan");
            return "redirect:/terminal/2";
        }
        model.addAttribute("dto", dto);
        return "barcode/terminal2";
    }

    @PostMapping("/3")
    public String putReadiedSerial(Dto dto, Model model, RedirectAttributes re){
        re.addFlashAttribute("dto", dto);
        if (dto.getInvoice() == null){
            re.addFlashAttribute("error", "Nakladnoy nomer kiritlmagan");
            return "redirect:/terminal";
        }
        if (dto.getSerial() == null){
            model.addAttribute("message", "Seriya yuq");
            return "barcode/terminal2";
        }
        String message = terminalService.init(dto.getInvoice(), dto.getSerial(), dto.isDelete());
        if (message.equals("BARKOD SOTILDI!!!!")){
            model.addAttribute("ok", "ok");
        }else {
            model.addAttribute("error", "error");
        }
        dto.setDelete(false);
        model.addAttribute("message", message);
        return "barcode/terminal2";
    }

    @GetMapping("/3/{id}")
    public String getSoldInfoForm(@PathVariable Long id, Model model){
        model.addAttribute("saleevents", invoiceService.getAllByInvoiceId(id));
        Dto dto = new Dto();
        dto.setInvoice(invoiceService.getById(id));
        model.addAttribute("dto", dto);
        return "barcode/terminal3";
    }

    @GetMapping("/3")    // if make get request
    public String toSelling(RedirectAttributes re){
        re.addFlashAttribute("error", "XATOLIK!");
        return "redirect:/terminal";
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
