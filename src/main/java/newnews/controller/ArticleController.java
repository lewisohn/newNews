package newnews.controller;

import java.io.IOException;
import newnews.domain.Article;
import newnews.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController extends MasterController {

    @Autowired
    private ArticleService artServ;

    @GetMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/article/{shortname}")
    public String getArticle(Model model, @PathVariable String shortname) {
        Article article = artServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/articles/new")
    public String getNewArticle(Model model) {
        return "admin/newarticle";
    }

    @PostMapping("/articles/new")
    public String postNewArticle(@RequestParam String name,
            //            @RequestParam("image") MultipartFile image,
            @RequestParam String standfirst,
            @RequestParam String text,
            @RequestParam("authors") Long[] authors,
            @RequestParam("categories") Long[] categories) throws IOException {
        artServ.addArticle(name,
                //               image,
                standfirst, text, authors, categories);
        return "redirect:/admin";
    }

}
