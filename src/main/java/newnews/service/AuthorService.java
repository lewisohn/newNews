package newnews.service;

import java.util.ArrayList;
import newnews.domain.Author;
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
        if (!name.equals("")) {
            Author a = findByName(name);
            if (a == null) {
                authors.save(new Author(name));
            }
        }
    }

    public Author findByName(String name) {
        return authors.findByName(name);
    }

}
