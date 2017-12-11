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

/**
 * Abstract controller that adds attributes to the model.
 *
 * @author Oliver
 */
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

    /**
     * Sets the Pageable elements for filtering entities.
     */
    public MasterController() {
        fiveByDateTime = PageRequest.of(0, 5, Sort.Direction.DESC, "dateTime");
        fiveByViews = PageRequest.of(0, 5, Sort.Direction.DESC, "views");
        tenByName = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
    }

    @ModelAttribute("categories")
    public Page<Category> modelCategories() {
        return catServ.findAll(tenByName);
    }

    @ModelAttribute("catSize")
    public Long modelCatSize() {
        return catServ.getSize();
    }

    @ModelAttribute("artSize")
    public Long modelArtSize() {
        return artServ.getSize();
    }

    @ModelAttribute("authSize")
    public Long modelAuthSize() {
        return authServ.getSize();
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
