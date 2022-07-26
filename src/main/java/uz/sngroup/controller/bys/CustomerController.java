package uz.sngroup.controller.bys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.bys.Customer;
import uz.sngroup.model.bys.Quality;
import uz.sngroup.service.bys.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired CustomerService customerService;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("customer", new Customer());
        return "models/customer";
    }

    @PostMapping
    public String create(Customer customer, RedirectAttributes re){
        String message = customerService.save(customer);
        re.addFlashAttribute("message", message);
        return "redirect:/customer";
    }

    @GetMapping("/u/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("customer", customerService.getById(id));
        return "models/customer";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes re){
        customerService.delete(id);
        re.addFlashAttribute("message", id + "Malumot O'chirildi!!!");
        return "redirect:/customer";
    }
}
