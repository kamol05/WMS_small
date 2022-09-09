package uz.sngroup.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.sngroup.service.report.StockService;

import javax.jws.WebParam;

@Controller
@RequestMapping("/stock")
public class StockReportController {
    @Autowired
    StockService stockService;

    @GetMapping("")
    public String getForm(Model model){
        model.addAttribute("events", stockService.getGroupByProductId());
        return "report/stock";
    }

    @GetMapping("/{id}")
    public String getAllByProductId(@PathVariable Long id, Model model){
        model.addAttribute("events", stockService.getAllByProductId(id));
        return "report/reportinstockbyproductid";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getStockPdfReport(){
        return stockService.getStockPdfReport();
    }

    @GetMapping("/p")
    public String getForm2(Model model){
        model.addAttribute("events", stockService.getGroupByProductId());
        return "report/stockprint";
    }
}
