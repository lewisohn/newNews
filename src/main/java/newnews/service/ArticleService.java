package newnews.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import newnews.domain.Article;
import newnews.domain.Author;
import newnews.domain.Category;
import newnews.domain.Trimmer;
import newnews.domain.Visit;
import newnews.repository.ArticleRepository;
import newnews.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for managing articles.
 *
 * @author Oliver
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articles;
    @Autowired
    private AuthorService authServ;
    @Autowired
    private CategoryService catServ;
    @Autowired
    private VisitRepository visits;

    /**
     * Creates and saved a new article to the repository.
     *
     * @param name The headline of the new article.
     * @param image The image of the new article.
     * @param standfirst The opening paragraph of the new article.
     * @param text The body of the new article.
     * @param authorIds A list of IDs of the authors of the new article.
     * @param categoryIds A list of IDs of the categories the new article
     * belongs to.
     * @throws IOException If the image is not a well-formatted MultipartFile.
     */
    public void addArticle(String name, MultipartFile image, String standfirst, String text, Long[] authorIds, Long[] categoryIds) throws IOException {
        if (validateNoImg(name, standfirst, text, authorIds, categoryIds) && validateImg(image)) {
            if (findByShortname(Trimmer.trim(name)) == null) {
                ArrayList<Author> authors = authServ.findMany(authorIds);
                ArrayList<Category> categories = catServ.findMany(categoryIds);
                Article article = new Article(authors, categories, name, standfirst, text);
                article.setImage(image.getBytes());
                articles.save(article);
            }
        }
    }

    /**
     * Deletes an article with the given shortname.
     *
     * @param shortname The shortname in question.
     */
    public void deleteArticle(String shortname) {
        Article article = findByShortname(shortname);
        article.getVisits().forEach((visit) -> {
            visits.delete(visit);
        });
        articles.delete(article);
    }

    /**
     * Updates an article with the given shortname.
     *
     * @param shortname The shortname of the edited article.
     * @param name The headline of the edited article.
     * @param image The image of the edited article.
     * @param standfirst The opening paragraph of the edited article.
     * @param text The body of the edited article.
     * @param authorIds A list of IDs of the authors of the edited article.
     * @param categoryIds A list of IDs of the categories the edited article
     * @throws IOException If the image is not a well-formatted MultipartFile.
     */
    public void editArticle(String shortname, String name, Optional<MultipartFile> image, String standfirst, String text, Long[] authorIds, Long[] categoryIds) throws IOException {
        if (validateNoImg(name, standfirst, text, authorIds, categoryIds)) {
            Article article = findByShortname(shortname);
            Article duplicate = findByShortname(Trimmer.trim(name));
            if (duplicate == null || duplicate.equals(article)) {
                if (image.isPresent() && validateImg(image.get())) {
                    article.setImage(image.get().getBytes());
                }
                article.setName(name);
                article.setStandfirst(standfirst);
                article.setText(text);
                article.setAuthors(authServ.findMany(authorIds));
                article.setCategories(catServ.findMany(categoryIds));
                article.updateShortname();
                articles.save(article);
            }
        }
    }

    /**
     * Creates a new visit and assigns it to the given article.
     *
     * @param article The article in question.
     */
    public void view(Article article) {
        Visit visit = new Visit(article);
        visits.save(visit);
        article.addVisit(visit);
        articles.save(article);
    }

    /* Getters, setters, overrides and private methods: no Javadoc */
    public Page<Article> findAll(Pageable pageable) {
        return articles.findAll(pageable);
    }

    public Article findByShortname(String shortname) {
        return articles.findByShortname(shortname);
    }

    public Long getSize() {
        return articles.count();
    }

    private boolean validateImg(MultipartFile image) {
        // Only JPEG and PNG files are accepted at this time.
        return (image != null && ("image/jpeg".equals(image.getContentType()) || "image/png".equals(image.getContentType())));
    }

    private boolean validateNoImg(String name, String standfirst, String text, Long[] authorIds, Long[] categoryIds) {
        // "new" is not a valid article shortname since /articles/new is the path of the new article creation page.
        return (name.length() > 0 && !Trimmer.trim(name).equals("new") && standfirst.length() > 0 && text.length() > 0 && authorIds.length > 0 && categoryIds.length > 0);
    }

}
