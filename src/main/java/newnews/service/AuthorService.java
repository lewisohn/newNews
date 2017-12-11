package newnews.service;

import java.util.ArrayList;
import newnews.domain.Author;
import newnews.domain.Trimmer;
import newnews.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authors;

    public Page<Author> findAll(Pageable pageable) {
        return authors.findAll(pageable);
    }

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

    public void addAuthor(String name) {
        if (validate(name)) {
            if (findByShortname(Trimmer.trim(name)) == null) {
                authors.save(new Author(name));
            }
        }
    }

    public Author findByName(String name) {
        return authors.findByName(name);
    }

    public Long size() {
        return authors.count();
    }

    public Author findByShortname(String shortname) {
        return authors.findByShortname(shortname);
    }

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

    private boolean validate(String name) {
        return (name.length() > 0);
    }

}
