package newnews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the control panel (admin) and login pages.
 *
 * @author Oliver
 */
@Controller
public class AdminController extends MasterController {

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin/admin";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "admin/login";
    }

}
