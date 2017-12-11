package newnews.controller;

import java.io.IOException;
import java.util.Optional;
import newnews.domain.Article;
import newnews.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ArticleController extends MasterController {

    @Autowired
    private ArticleService artServ;

    @GetMapping("/articles/{shortname}")
    public String getArticle(Model model, @PathVariable String shortname) {
        Article article = artServ.findByShortname(shortname.toLowerCase());
        artServ.view(article);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/articles/{shortname}/edit")
    public String getEditArticle(Model model, @PathVariable String shortname) {
        Article article = artServ.findByShortname(shortname.toLowerCase());
        model.addAttribute("article", article);
        return "admin/editarticle";
    }

    @GetMapping(path = "/articles/{shortname}/image", produces = {"image/jpeg", "image/png"})
    @ResponseBody
    public byte[] getImage(@PathVariable String shortname) {
        Article article = artServ.findByShortname(shortname.toLowerCase());
        return article.getImage();
    }

    @GetMapping(value = {"/", "/articles"})
    public String getIndex() {
        return "index";
    }

    @GetMapping("/articles/new")
    public String getNewArticle(Model model) {
        model.addAttribute("article", new Article());
        return "admin/newarticle";
    }

    @PostMapping("/articles/{shortname}/edit")
    public String postEditArticle(@PathVariable String shortname,
            @RequestParam String name,
            @RequestParam("image") Optional<MultipartFile> image,
            @RequestParam String standfirst,
            @RequestParam String text,
            @RequestParam("authors") Long[] authors,
            @RequestParam("categories") Long[] categories) throws IOException {
        artServ.editArticle(shortname, name, image, standfirst, text, authors, categories);
        return "redirect:/admin";
    }

    @PostMapping("/articles/new")
    public String postNewArticle(@RequestParam String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam String standfirst,
            @RequestParam String text,
            @RequestParam("authors") Long[] authors,
            @RequestParam("categories") Long[] categories) throws IOException {
        artServ.addArticle(name, image, standfirst, text, authors, categories);
        return "redirect:/admin";
    }

}
