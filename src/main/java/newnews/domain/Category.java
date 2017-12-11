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

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Category extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

//    @Getter(AccessLevel.NONE)
//    private LocalDate lastActive;
    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;
    private String name;
    private String shortname;

    public Category(String name) {
        this.name = name;
        updateShortname();
    }

    public int getSize() {
        return articles.size();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getLastActive() {
        if (!articles.isEmpty()) {
            Collections.sort(articles);
            return articles.get(0).getDate().toString();
        } else {
            return "Never";
        }
    }

    public boolean isCategoryOf(Article article) {
        return articles.contains(article);
    }

    public final void updateShortname() {
        shortname = Trimmer.trim(name);
    }

}
