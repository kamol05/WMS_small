package uz.sngroup.controller.bys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.sngroup.model.bys.Quality;
import uz.sngroup.service.bys.QualityService;

@Controller
@RequestMapping("/quality")
public class QualityController {
    @Autowired QualityService qualityService;

    @GetMapping
    public String getForm(Model model){
        model.addAttribute("qualities", qualityService.getAll());
        model.addAttribute("quality", new Quality());
        return "models/quality";
    }

    @PostMapping
    public String create(Quality quality, RedirectAttributes re){
        String message = qualityService.save(quality);
        re.addFlashAttribute("message", message);
        return "redirect:/quality";
    }

    @GetMapping("/u/{id}")
    public String update(@PathVariable Long id, Model model){
        model.addAttribute("qualities", qualityService.getAll());
        model.addAttribute("quality", qualityService.getById(id));
        return "models/quality";
    }

    @GetMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes re){
        qualityService.delete(id);
        re.addFlashAttribute("message", id + "Malumot O'chirildi!!!");
        return "redirect:/quality";
    }


}
