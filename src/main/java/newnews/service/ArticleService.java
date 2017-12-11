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

    public Page<Article> findAll(Pageable pageable) {
        return articles.findAll(pageable);
    }

    public void addArticle(String name, MultipartFile image, String standfirst, String text, Long[] authorIds, Long[] categoryIds) throws IOException {
        if (validateNoImg(name, standfirst, text, authorIds, categoryIds) && validateImg(image)) {
            if (findByShortname(Trimmer.trim(name)) == null) {
                ArrayList<Author> authors = authServ.findMany(authorIds);
                ArrayList<Category> categories = catServ.findMany(categoryIds);
                Article article = new Article(authors, categories, name, standfirst, text);
                article.addImage(image.getBytes());
                articles.save(article);
            }
        }
    }

    public Article findByShortname(String shortname) {
        return articles.findByShortname(shortname);
    }

    public void view(Article article) {
        Visit visit = new Visit(article);
        visits.save(visit);
        article.addVisit(visit);
        articles.save(article);
    }

    public Long size() {
        return articles.count();
    }

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

    private boolean validateNoImg(String name, String standfirst, String text, Long[] authorIds, Long[] categoryIds) {
        return (name.length() > 0 && !name.toLowerCase().equals("new") && standfirst.length() > 0 && text.length() > 0 && authorIds.length > 0 && categoryIds.length > 0);
    }

    private boolean validateImg(MultipartFile image) {
        return (image != null && ("image/jpeg".equals(image.getContentType()) || "image/png".equals(image.getContentType())));
    }

}
