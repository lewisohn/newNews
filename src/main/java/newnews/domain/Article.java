package newnews.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Article extends AbstractPersistable<Long> implements Serializable, Comparable<Article> {

    @Id
    private Long id;

    @OneToMany
    private List<Visit> visits;
    @Lob
    private byte[] image;
    @Getter(AccessLevel.NONE)
    private int views;
    @ManyToMany
    private List<Author> authors;
    @ManyToMany
    private List<Category> categories;
    private LocalDateTime dateTime;
    private String name;
    private String shortname;
    private String standfirst;
    @Column(columnDefinition = "LONGVARCHAR")
    private String text;

    public Article(List<Author> authors, List<Category> categories, String name, String standfirst, String text) {
        this.authors = authors;
        this.categories = categories;
        this.dateTime = LocalDateTime.now();
        this.name = name;
        updateShortname();
        this.standfirst = standfirst;
        this.text = text;
        this.visits = new ArrayList<>();
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getViews() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Visit> it = visits.iterator();
        while (it.hasNext()) {
            Visit visit = it.next();
            long seconds = ChronoUnit.SECONDS.between(visit.getDateTime(), now);
            if (seconds > 604800) { // 604800 seconds in one week
                it.remove();
            } else {
                break;
            }
        }
        return visits.size();
    }

    public void addImage(byte[] image) {
        this.image = image;
    }

    public final void updateShortname() {
        shortname = Trimmer.trim(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Article a) {
        return a.dateTime.compareTo(this.dateTime);
    }


}
