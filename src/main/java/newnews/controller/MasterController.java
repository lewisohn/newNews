package newnews.controller;

import newnews.domain.Article;
import newnews.domain.Author;
import newnews.domain.Category;
import newnews.service.ArticleService;
import newnews.service.AuthorService;
import newnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class MasterController {

    @Autowired
    private ArticleService artServ;
    @Autowired
    private AuthorService authServ;
    @Autowired
    private CategoryService catServ;
    private final Pageable fiveByDateTime;
    private final Pageable fiveByViews;
    private final Pageable tenByName;

    public MasterController() {
        fiveByDateTime = PageRequest.of(0, 5, Sort.Direction.ASC, "dateTime");
        fiveByViews = PageRequest.of(0, 5, Sort.Direction.ASC, "views");
        tenByName = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
    }

    @ModelAttribute("categories")
    public Page<Category> modelCategories() {
        return catServ.findAll(tenByName);
    }

    @ModelAttribute("latest")
    public Page<Article> modelLatestArticles() {
        return artServ.findAll(fiveByDateTime);
    }

    @ModelAttribute("popular")
    public Page<Article> modelPopularArticles() {
        return artServ.findAll(fiveByViews);
    }

    @ModelAttribute("authors")
    public Page<Author> modelAuthors() {
        return authServ.findAll(tenByName);
    }

}
