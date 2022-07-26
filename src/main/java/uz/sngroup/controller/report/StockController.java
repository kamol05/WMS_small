package uz.sngroup.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.sngroup.service.report.StockService;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    StockService stockService;

    @GetMapping("")
    public String getForm(Model model){
        model.addAttribute("events", stockService.getGroupByProductId());
        return "report/stock";
    }

    @GetMapping("/{id}")
    public String getAllByProductId(@PathVariable Long id){
        return "redirect:/stock";
    }
}
