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
 * An author of articles. Must have a name.
 *
 * @author Oliver
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Author extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "authors")
    private List<Article> articles;
    private String name;
    private String shortname;

    /**
     * Creates a new author with the given name.
     *
     * @param name The full name of this author.
     */
    public Author(String name) {
        this.name = name;
        updateShortname();
    }

    /**
     * Checks if this author wrote a specified article.
     *
     * @param article The article in question.
     * @return True if this author wrote the article, false otherwise.
     */
    public boolean isAuthorOf(Article article) {
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
