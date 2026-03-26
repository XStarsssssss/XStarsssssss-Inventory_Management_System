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

        // CATEGORIES
        Category electronics = categoryRepo.createCategory("Electronics");
        SubCategory mobile = categoryRepo.createSubCategory(electronics, "Mobile");
        SubCategory laptop = categoryRepo.createSubCategory(electronics, "Laptop");

        Category food = categoryRepo.createCategory("Food");
        SubCategory fruit = categoryRepo.createSubCategory(food, "Fruit");
        SubCategory vegetable = categoryRepo.createSubCategory(food, "Vegetable");
        SubCategory drink = categoryRepo.createSubCategory(food, "Drink");

        Category robot = categoryRepo.createCategory("Robot");
        SubCategory industrialRobot = categoryRepo.createSubCategory(robot, "Industrial");
        SubCategory serviceRobot = categoryRepo.createSubCategory(robot, "Service");

        Category phone = categoryRepo.createCategory("Phone");
        SubCategory ios = categoryRepo.createSubCategory(phone, "iOS");
        SubCategory android = categoryRepo.createSubCategory(phone, "Android");

        //PRODUCTS

// Electronics
        productRepo.add(new Product(
                productRepo.generateId(),
                "iPhone 14", 10, 700, 1000, electronics, mobile));

        productRepo.add(new Product(
                productRepo.generateId(),
                "MacBook Pro", 6, 1500, 2000, electronics, laptop));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Android Tablet", 10, 200, 350, electronics, mobile));


// Food
        productRepo.add(new Product(
                productRepo.generateId(),
                "Apple", 50, 1, 2, food, fruit));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Carrot", 30, 0.5, 1.0, food, vegetable));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Orange Juice", 20, 1.0, 2.5, food, drink));


// Robot
        productRepo.add(new Product(
                productRepo.generateId(),
                "ABB Robot Arm", 5, 5000, 7500, robot, industrialRobot));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Cleaning Robot", 10, 200, 350, robot, serviceRobot));


// Phone
        productRepo.add(new Product(
                productRepo.generateId(),
                "iPhone 15 Pro", 8, 900, 1300, phone, ios));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Samsung S23", 12, 600, 900, phone, android));

        productRepo.add(new Product(
                productRepo.generateId(),
                "Xiaomi 13", 15, 400, 650, phone, android));
    }
}