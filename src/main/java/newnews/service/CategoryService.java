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

/**
 * Service for managing categories.
 *
 * @author Oliver
 */
@Data
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categories;

    /**
     * Adds a new category with the given name.
     *
     * @param name The full name of the category.
     */
    public void addCategory(String name) {
        if (validate(name)) {
            if (findByName(Trimmer.trim(name)) == null) {
                categories.save(new Category(name));
            }
        }
    }

    /**
     * Deletes a category with the given shortname.
     *
     * @param shortname The shortname in question.
     */
    public void deleteCategory(String shortname) {
        categories.deleteByShortname(shortname);
    }

    /**
     * Updates an existing category with the given shortname. Will not save the
     * new name unless it generates a unique shortname.
     *
     * @param shortname The old shortname of the edited category.
     * @param name The new name of the edited category.
     */
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

    /**
     * Converts an array of category IDs to a list of categories.
     *
     * @param ids The IDs to find.
     * @return A list of categories with the given IDs.
     */
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

    /* Getters, setters, overrides and private methods: no Javadoc */
    public Page<Category> findAll(Pageable pageable) {
        return categories.findAll(pageable);
    }

    public Category findByName(String name) {
        return categories.findByName(name);
    }

    public Category findByShortname(String shortname) {
        return categories.findByShortname(shortname);
    }

    public Long getSize() {
        return categories.count();
    }

    private boolean validate(String name) {
        // "new" is not a valid article shortname since /categories/new is the path of the new article creation page.
        return (name.length() > 0 && !Trimmer.trim(name).equals("new"));
    }

}
