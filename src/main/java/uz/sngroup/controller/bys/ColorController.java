package uz.sngroup.controller.bys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.bys.Color;
import uz.sngroup.service.bys.ColorService;

@Controller
@RequestMapping("/color")
public class ColorController {
    @Autowired ColorService colorService;


    @GetMapping
    public String getForm(Model model){
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("color", new Color());
        return "models/color";
    }

    @PostMapping
    public String create(Color color, RedirectAttributes re){
        String message = colorService.save(color);
        re.addFlashAttribute("message", message);
        return "redirect:/color";
    }

    @GetMapping("/u/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("colors", colorService.getAll());
        model.addAttribute("color", colorService.getById(id));
        return "models/color";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes re){
        colorService.delete(id);
        re.addFlashAttribute("message", id + "Malumot O'chirildi!!!");
        return "redirect:/color";
    }
}
