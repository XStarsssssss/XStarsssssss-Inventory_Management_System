package repository;

import model.Category;
import model.Product;
import model.SubCategory;
import model.User;

public class FakeDatabases {

    public static void initialize() {

        CategoryRepository categoryRepo = CategoryRepository.getInstance();
        ProductRepository productRepo = ProductRepository.getInstance();
        UserRepository userRepo = UserRepository.getInstance();

        // Users
        userRepo.add(new User("admin", "admin", User.Role.ADMIN));
        userRepo.add(new User("staff", "staff", User.Role.STAFF));

        // Categories
        Category electronics = categoryRepo.createCategory("Electronics");
        SubCategory mobile = categoryRepo.createSubCategory(electronics, "Mobile");
        SubCategory laptop = categoryRepo.createSubCategory(electronics, "Laptop");

        Category food = categoryRepo.createCategory("Food");
        SubCategory fruit = categoryRepo.createSubCategory(food, "Fruit");

        // Products
        productRepo.add(new Product(
                productRepo.generateId(),
                "iPhone 14", 10, 700, 1000, electronics, mobile));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Apple", 50, 1, 2, food, fruit));
    }
}