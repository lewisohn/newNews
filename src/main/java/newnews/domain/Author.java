package newnews.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Author extends AbstractPersistable<Long> implements Serializable {

    @Id
    private Long id;

}
