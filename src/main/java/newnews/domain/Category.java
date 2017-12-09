package newnews.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private String name;
    private String shortname;

    public Category(String name) {
        this.name = name;
        this.shortname = name.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }
    
    

}
