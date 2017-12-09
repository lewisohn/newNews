package newnews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewNewsController extends MasterController {

    @GetMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

}
