package uz.sngroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.sngroup.service.InitialSetup;
import uz.sngroup.service.report.StockService;

@Controller
@RequestMapping("/pivot")
public class PivotController {
    @Autowired StockService stockService;
    @Autowired InitialSetup initialSetup;

    @GetMapping("/onfirststart")
    public String onFirstSetup(){
        try {
            initialSetup.onFisrtStart();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/instock")
    public String getForm(Model model){
        model.addAttribute("events", stockService.findAllInStock());
        return "pivot/pivot";
    }

    @GetMapping("/all")
    public String getForm2(Model model){
        model.addAttribute("events", stockService.findAll());
        return "pivot/pivot";
    }

    @GetMapping("/sold")
    public String getForm3(Model model){
        model.addAttribute("events", stockService.findAllSelled());
        return "pivot/pivot";
    }
}
