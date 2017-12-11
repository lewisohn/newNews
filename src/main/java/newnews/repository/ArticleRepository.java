package newnews.repository;

import newnews.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Article repository.
 *
 * @author Oliver
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    void deleteByShortname(String shortname);

    Article findByName(String name);

    Article findByShortname(String shortname);

}
