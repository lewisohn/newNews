package newnews.controller;

import newnews.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authServ;

    @GetMapping("/authors/new")
    public String getNewAuthor(Model model) {
        return "admin/newauthor";
    }

    @PostMapping("/authors/new")
    public String postNewAuthor(@RequestParam String name) {
        authServ.addAuthor(name);
        return "redirect:/admin";
    }

}
