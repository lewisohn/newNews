package newnews.controller;

import newnews.domain.Category;
import newnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController extends MasterController {

    @Autowired
    private CategoryService cs;

    @GetMapping("/category/{shortname}")
    public String getCategory(Model model, @PathVariable String shortname) {
        Category category = cs.findByShortname(shortname.toLowerCase());
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        return "categories";
    }

    @PostMapping("/categories")
    public String postCategories(@RequestParam String name) {
        cs.addCategory(name);
        return "redirect:/categories";
    }

}
