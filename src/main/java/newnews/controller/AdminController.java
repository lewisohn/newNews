package newnews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController extends MasterController {

    @GetMapping("/login")
    public String getLogin() {
        return "admin/login";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin/admin";
    }

}
