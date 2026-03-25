package repository;

import model.Category;
import model.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static CategoryRepository instance;
    private List<Category> categories = new ArrayList<>();
    private int categoryId = 1;
    private int subCategoryId = 1;

    private CategoryRepository() {}

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    public Category createCategory(String name) {
        Category c = new Category(categoryId++, name);
        categories.add(c);
        return c;
    }

    public SubCategory createSubCategory(Category category, String name) {
        SubCategory sub = new SubCategory(subCategoryId++, name);
        category.addSubCategory(sub);
        return sub;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category findCategoryById(int id) {
        for (Category c : categories) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public SubCategory findSubCategoryById(Category category, int id) {
        if (category == null) return null;
        for (SubCategory s : category.getSubCategories()) {
            if (s.getId() == id) return s;
        }
        return null;
    }
}