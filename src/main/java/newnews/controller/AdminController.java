package newnews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController extends MasterController {

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "admin/login";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        return "admin/admin";
    }

}
