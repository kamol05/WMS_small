package uz.sngroup.controller.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.Barcode;
import uz.sngroup.model.event.Event;
import uz.sngroup.service.event.BarcodeService;
import uz.sngroup.service.event.EventService;

import java.util.List;

@Controller
@RequestMapping("/barcode")
public class BarcodeController {
    @Autowired BarcodeService barcodeService;
    @Autowired
    EventService eventService;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("barcode", new Barcode());
        model.addAttribute("qualities", barcodeService.getQualities());
        model.addAttribute("gramms", barcodeService.getGramms());
        model.addAttribute("colors", barcodeService.getColors());
        return "barcode/barcode";
    }

    @PostMapping
    public String print(Barcode barcode, RedirectAttributes re){
        String message = barcodeService.init(barcode);
        re.addFlashAttribute("m1", message);
        return "redirect:/barcode";
    }

    @GetMapping("/info")
    public String getFormForInfo(Integer serial, Model model){
        if (serial == null){
            return "barcode/info";
        }
        List<Event> events = eventService.getAllBySerialNumber(serial);
        if (events.size() > 0) {
            model.addAttribute("serial", serial);
            model.addAttribute("events", events);
            return "barcode/info";
        }
        model.addAttribute("message", "BARKOD TOPILMADI!!!");
        return "barcode/info";
    }

    @GetMapping("/delete")
    public String deleteBarcode(Integer serial, Model model){
        if (serial == null){
            model.addAttribute("message", "XATO!!!");
            return "barcode/info";
        }
        String message = eventService.deleteBySerial(serial);
        model.addAttribute("message", message);
        return "barcode/info";
    }

    @GetMapping("/delete/{serial}/&/{productid}")
    public String deleteBarcode2(@PathVariable Integer serial, @PathVariable Long productid, RedirectAttributes re){
        if (serial == null || productid == null){
            return "redirect:/stock";
        }
        String message = eventService.deleteBySerial(serial);
        re.addFlashAttribute("message", message);
        return "redirect:/stock/" + productid;
    }

}
