package newnews.service;

import java.util.List;
import lombok.Data;
import newnews.domain.Category;
import newnews.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Data
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categories;

    public List<Category> findAll() {
        return categories.findAll();
    }

    public Page<Category> findAll(Pageable pageable) {
        return categories.findAll(pageable);
    }

    public Category findByName(String name) {
        return categories.findByName(name);
    }

    public Category findByShortname(String shortname) {
        return categories.findByShortname(shortname);
    }

    public void addCategory(String name) {
        if (!name.equals("")) {
            Category c = findByName(name);
            if (c == null) {
                categories.save(new Category(name));
            }
        }
    }

}
