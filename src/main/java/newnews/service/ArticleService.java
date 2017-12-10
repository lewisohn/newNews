package newnews.service;

import java.io.IOException;
import java.util.ArrayList;
import newnews.domain.Article;
import newnews.domain.Author;
import newnews.domain.Category;
import newnews.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articles;
    @Autowired
    private AuthorService authServ;
    @Autowired
    private CategoryService catServ;

    public Page<Article> findAll(Pageable pageable) {
        return articles.findAll(pageable);
    }

    public void addArticle(String name,
            //            MultipartFile image,
            String standfirst,
            String text, Long[] authorIds, Long[] categoryIds) throws IOException {
        if ( //                image != null
                //                && ("image/jpeg".equals(image.getContentType()) || "image/png".equals(image.getContentType()))
                //                &&
                !name.equals("") && !standfirst.equals("") && !text.equals("")
                && authorIds.length > 0 && categoryIds.length > 0) {
            Article a = articles.findByName(name);
            // Potential problem: two articles could have different long names but the same short names and URLs
            if (a == null) {
                ArrayList<Author> authors = authServ.findMany(authorIds);
                ArrayList<Category> categories = catServ.findMany(categoryIds);
                
                articles.save(new Article(
                        //                        image.getBytes(),
                        authors, categories,
                        name, standfirst, text));
            }
        }
    }

    public Article findByShortname(String shortname) {
        return articles.findByShortname(shortname);
    }
}
