package uz.sngroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.sngroup.service.report.StockService;

@Controller
@RequestMapping("pivot")
public class PivotController {
    @Autowired
    StockService stockService;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("events", stockService.getGroupByProductId());
        return "pivot/pivot";
    }
}
