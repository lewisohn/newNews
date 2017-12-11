package newnews.controller;

import javax.transaction.Transactional;
import newnews.domain.Category;
import newnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for listing, viewing, creating, editing and deleting categories.
 *
 * @author Oliver
 */
@Controller
public class CategoryController extends MasterController {

    @Autowired
    private CategoryService catServ;

    @Transactional
    @DeleteMapping("/categories/{shortname}/delete")
    public String deleteCategory(@PathVariable String shortname) {
        catServ.deleteCategory(shortname);
        return "redirect:/admin";
    }

    @GetMapping("/categories")
    public String getCategories() {
        return "categories";
    }

    @GetMapping("/categories/{shortname}**")
    public String getCategory(Model model, @PathVariable String shortname) {
        Category category = catServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("category", category);
        return "category";
    }

    @GetMapping("/categories/{shortname}/delete")
    public String getDeleteCategory(Model model, @PathVariable String shortname) {
        Category category = catServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("category", category);
        return "admin/deletecategory";
    }

    @GetMapping("/categories/{shortname}/edit")
    public String getEditCategory(Model model, @PathVariable String shortname) {
        Category category = catServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("category", category);
        return "admin/editcategory";
    }

    @GetMapping("/categories/new")
    public String getNewCategory() {
        return "admin/newcategory";
    }

    @PostMapping("/categories/{shortname}/edit")
    public String postEditCategory(@PathVariable String shortname, @RequestParam String name) {
        catServ.editCategory(shortname, name);
        return "redirect:/admin";
    }

    @PostMapping("/categories/new")
    public String postNewCategory(@RequestParam String name) {
        catServ.addCategory(name);
        return "redirect:/admin";
    }

}
