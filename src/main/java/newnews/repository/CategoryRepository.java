package newnews.repository;

import newnews.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Category repository.
 *
 * @author Oliver
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteByShortname(String shortname);

    Category findByName(String name);

    Category findByShortname(String shortname);

}
