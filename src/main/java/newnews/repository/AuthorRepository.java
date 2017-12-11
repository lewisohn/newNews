package newnews.repository;

import newnews.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author repository.
 *
 * @author Oliver
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    void deleteByShortname(String shortname);

    Author findByName(String name);

    Author findByShortname(String shortname);

}
