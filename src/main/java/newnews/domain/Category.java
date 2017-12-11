package newnews.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * A category of articles. Must have a name.
 *
 * @author Oliver
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Category extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;
    private String name;
    private String shortname;

    /**
     * Creates a new category with the given name.
     *
     * @param name The full name of this category.
     */
    public Category(String name) {
        this.name = name;
        updateShortname();
    }

    /**
     * Checks if this category is assigned to a specific article.
     *
     * @param article The article in question.
     * @return True if the article is in this category, false otherwise.
     */
    public boolean isCategoryOf(Article article) {
        return articles.contains(article);
    }

    /**
     * Updates the shortname for this article based on its name.
     */
    public final void updateShortname() {
        shortname = Trimmer.trim(name);
    }

    /* Getters, setters, overrides and private methods: no Javadoc */
    public String getLastActive() {
        if (!articles.isEmpty()) {
            Collections.sort(articles);
            return articles.get(0).getDate().toString();
        } else {
            return "Never";
        }
    }

    public Article getLatest() {
        Collections.sort(articles);
        return articles.get(0);
    }

    public int getSize() {
        return articles.size();
    }

    @Override
    public String toString() {
        return name;
    }

}
