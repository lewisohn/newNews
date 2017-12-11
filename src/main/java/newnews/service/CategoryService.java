package newnews.service;

import java.util.ArrayList;
import lombok.Data;
import newnews.domain.Category;
import newnews.domain.Trimmer;
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

    public Page<Category> findAll(Pageable pageable) {
        return categories.findAll(pageable);
    }

    public Category findByName(String name) {
        return categories.findByName(name);
    }

    public Category findByShortname(String shortname) {
        return categories.findByShortname(shortname);
    }

    public ArrayList<Category> findMany(Long[] ids) {
        ArrayList<Category> found = new ArrayList<>();
        for (Long id : ids) {
            Category c = categories.getOne(id);
            if (c != null) {
                found.add(c);
            }
        }
        return found;
    }

    public void addCategory(String name) {
        if (validate(name)) {
            if (findByName(Trimmer.trim(name)) == null) {
                categories.save(new Category(name));
            }
        }
    }

    public Long size() {
        return categories.count();
    }

    public void editCategory(String shortname, String name) {
        if (validate(name)) {
            Category category = findByShortname(shortname);
            Category duplicate = findByShortname(Trimmer.trim(name));
            if (duplicate == null || duplicate.equals(category)) {
                category.setName(name);
                category.updateShortname();
                categories.save(category);
            }
        }
    }

    private boolean validate(String name) {
        return (name.length() > 0);
    }

}
