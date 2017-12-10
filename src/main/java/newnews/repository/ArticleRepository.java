package newnews.repository;

import newnews.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByName(String name);

    Article findByShortname(String shortname);

}
