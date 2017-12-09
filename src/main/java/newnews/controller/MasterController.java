package newnews.controller;

import newnews.domain.Category;
import newnews.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class MasterController {

    @Autowired
    private CategoryService catServ;

    @ModelAttribute("categories")
    public Page<Category> modelCategories() {
        Pageable ten = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        return catServ.findAll(ten);
    }

}
