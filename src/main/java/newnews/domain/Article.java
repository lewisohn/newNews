package newnews.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Article extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

    @Lob
    private byte[] image;
    private int views;
    @ManyToMany
    private List<Author> authors;
    @ManyToMany
    private List<Category> categories;
    private LocalDateTime dateTime;
    private String name;
    private String shortname;
    private String standfirst;
    private String text;

    public Article(
            //            byte[] image,
            List<Author> authors, List<Category> categories, String name, String standfirst, String text) {
//        this.image = image;
        this.authors = authors;
        this.categories = categories;
        this.dateTime = LocalDateTime.now();
        this.name = name;
        this.shortname = Utilities.trim(name);
        this.standfirst = standfirst;
        this.text = text;
        this.views = 0;
    }

    public void view() {
        views++;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public List<Author> getAuthors() {
        return authors;
    }

}
