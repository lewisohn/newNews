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
    
    public Visit(Article article) {
        this.article = article;
        this.dateTime = LocalDateTime.now();
    }

}
