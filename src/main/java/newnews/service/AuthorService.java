package newnews.service;

import java.util.ArrayList;
import newnews.domain.Author;
import newnews.domain.Trimmer;
import newnews.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service for managing authors.
 *
 * @author Oliver
 */
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authors;

    /**
     * Adds a new author with the given name.
     *
     * @param name The full name of the author.
     */
    public void addAuthor(String name) {
        if (validate(name)) {
            if (findByShortname(Trimmer.trim(name)) == null) {
                authors.save(new Author(name));
            }
        }
    }

    /**
     * Deletes an author with the given shortname.
     *
     * @param shortname The shortname in question.
     */
    public void deleteAuthor(String shortname) {
        authors.deleteByShortname(shortname);
    }

    /**
     * Updates an existing author with the given shortname. Will not save the
     * new name unless it generates a unique shortname.
     *
     * @param shortname The old shortname of the edited author.
     * @param name The new name of the edited author.
     */
    public void editAuthor(String shortname, String name) {
        if (validate(name)) {
            Author author = findByShortname(shortname);
            Author duplicate = findByShortname(Trimmer.trim(name));
            if (duplicate == null || duplicate.equals(author)) {
                author.setName(name);
                author.updateShortname();
                authors.save(author);
            }
        }
    }

    /**
     * Converts an array of author IDs to a list of authors.
     *
     * @param ids The IDs to find.
     * @return A list of authors with the given IDs.
     */
    public ArrayList<Author> findMany(Long[] ids) {
        ArrayList<Author> found = new ArrayList<>();
        for (Long id : ids) {
            Author a = authors.getOne(id);
            if (a != null) {
                found.add(a);
            }
        }
        return found;
    }

    /* Getters, setters, overrides and private methods: no Javadoc */
    public Page<Author> findAll(Pageable pageable) {
        return authors.findAll(pageable);
    }

    public Author findByName(String name) {
        return authors.findByName(name);
    }

    public Author findByShortname(String shortname) {
        return authors.findByShortname(shortname);
    }

    public Long getSize() {
        return authors.count();
    }

    private boolean validate(String name) {
        // "new" is not a valid article shortname since /authors/new is the path of the new article creation page.
        return (name.length() > 0 && !Trimmer.trim(name).equals("new"));
    }

}
