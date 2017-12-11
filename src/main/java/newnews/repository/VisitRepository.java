package newnews.repository;

import newnews.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Visit repository.
 *
 * @author Oliver
 */
public interface VisitRepository extends JpaRepository<Visit, Long> {

}
