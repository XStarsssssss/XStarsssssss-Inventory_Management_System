package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<SubCategory> subCategories = new ArrayList<>();

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(SubCategory sub) {
        subCategories.add(sub);
    }
}