package newnews.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * A record of one user's visit to one article.
 *
 * @author Oliver
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Visit extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

    @ManyToOne
    private Article article;
    private LocalDateTime dateTime;

    /**
     * Creates a new visit for the specified article.
     *
     * @param article The article that has just been visited.
     */
    public Visit(Article article) {
        this.article = article;
        this.dateTime = LocalDateTime.now();
    }

}
