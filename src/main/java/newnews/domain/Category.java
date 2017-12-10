package newnews.domain;

import java.io.Serializable;
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

    @ManyToMany(mappedBy = "categories")
    private List<Article> articles;
    private String name;
    private String shortname;

    public Category(String name) {
        this.name = name;
        this.shortname = Utilities.trim(name);
    }

    public int getSize() {
        return articles.size();
    }

}
