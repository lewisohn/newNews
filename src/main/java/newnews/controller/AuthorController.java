package newnews.controller;

import javax.transaction.Transactional;
import newnews.domain.Author;
import newnews.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for listing, viewing, creating, editing and deleting authors.
 *
 * @author Oliver
 */
@Controller
public class AuthorController extends MasterController {

    @Autowired
    private AuthorService authServ;

    @Transactional
    @DeleteMapping("/authors/{shortname}/delete")
    public String deleteAuthor(@PathVariable String shortname) {
        authServ.deleteAuthor(shortname);
        return "redirect:/admin";
    }

    @GetMapping("/authors/{shortname}")
    public String getAuthor(Model model, @PathVariable String shortname) {
        Author author = authServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/authors")
    public String getAuthors() {
        return "authors";
    }

    @GetMapping("/authors/{shortname}/delete")
    public String getDeleteAuthor(Model model, @PathVariable String shortname) {
        Author author = authServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("author", author);
        return "admin/deleteauthor";
    }

    @GetMapping("/authors/{shortname}/edit")
    public String getEditAuthor(Model model, @PathVariable String shortname) {
        Author author = authServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("author", author);
        return "admin/editauthor";
    }

    @GetMapping("/authors/new")
    public String getNewAuthor() {
        return "admin/newauthor";
    }

    @PostMapping("/authors/{shortname}/edit")
    public String postEditAuthor(@PathVariable String shortname, @RequestParam String name) {
        authServ.editAuthor(shortname, name);
        return "redirect:/admin";
    }

    @PostMapping("/authors/new")
    public String postNewAuthor(@RequestParam String name) {
        authServ.addAuthor(name);
        return "redirect:/admin";
    }

}
