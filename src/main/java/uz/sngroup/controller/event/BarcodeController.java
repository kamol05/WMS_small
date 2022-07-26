package uz.sngroup.controller.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.Barcode;
import uz.sngroup.service.event.BarcodeService;

@Controller
@RequestMapping("/barcode")
public class BarcodeController {
    @Autowired BarcodeService barser;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("barcode", new Barcode());
        model.addAttribute("qualities", barser.getQualities());
        model.addAttribute("gramms", barser.getGramms());
        model.addAttribute("colors", barser.getColors());
        return "barcode/barcode";
    }

    @PostMapping
    public String print(Barcode barcode, RedirectAttributes re){
        String message = barser.init(barcode);
        re.addFlashAttribute("m1", message);
        return "redirect:/barcode";
    }
}
