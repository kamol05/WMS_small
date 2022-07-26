package uz.sngroup.controller.bys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.bys.Gramm;
import uz.sngroup.service.bys.GrammService;

@Controller
@RequestMapping("/gramm")
public class GrammController {
    @Autowired GrammService grammService;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("gramms", grammService.getAll());
        model.addAttribute("gramm", new Gramm());
        return "models/gramm";
    }

    @PostMapping
    public String create(Gramm gramm, RedirectAttributes re){
        String message = grammService.save(gramm);
        re.addFlashAttribute("message", message);
        return "redirect:/gramm";
    }

    @GetMapping("/u/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("gramms", grammService.getAll());
        model.addAttribute("gramm", grammService.getById(id));
        return "models/gramm";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes re){
        grammService.delete(id);
        re.addFlashAttribute("message", id + "Malumot O'chirildi!!!");
        return "redirect:/gramm";
    }
}
