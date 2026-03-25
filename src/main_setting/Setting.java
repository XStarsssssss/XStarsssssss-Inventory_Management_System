package main_setting;

import model.Category;
import model.SubCategory;
import model.User;
import repository.CategoryRepository;
import repository.FakeDatabases;
import repository.ProductRepository;
import repository.UserRepository;
import service.AdminService;
import service.ProductService;

import java.util.Scanner;

public class Setting {

    String back = "b";
    int intback = 0;

    private Scanner sc = new Scanner(System.in);

    private ProductService productService;
    private AdminService adminService;

    private CategoryRepository categoryRepo;
    private ProductRepository productRepo;
    private UserRepository userRepo;

    public Setting() {
        // Initialize fake database
        FakeDatabases.initialize();

        categoryRepo = CategoryRepository.getInstance();
        productRepo = ProductRepository.getInstance();
        userRepo = UserRepository.getInstance();

        productService = new ProductService(productRepo);
        adminService = new AdminService(categoryRepo);
    }

    public void start() {
        while (true) {
            User user = login();

            boolean logout = false;
            while (!logout) {
                if (user.getRole() == User.Role.ADMIN) {
                    logout = adminMenu();
                } else {
                    logout = staffMenu();
                }
            }
        }
    }

    // ================= LOGIN =================
    private User login() {
        while (true) {
            System.out.println("\n=== LOGIN (Admin or Staff) ===");
            System.out.print("Username: ");
            String u = sc.nextLine();

            System.out.print("Password: ");
            String p = sc.nextLine();

            User user = userRepo.authenticate(u, p);

            if (user != null) {
                System.out.println("Welcome " + user.getUsername()
                        + " | Role: " + user.getRole());
                return user;
            }

            System.out.println("Invalid credentials!");
        }
    }

    // ================= ADMIN MENU =================
    private boolean adminMenu() {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. View Products");
        System.out.println("2. Create Category");
        System.out.println("3. Create SubCategory");
        System.out.println("4. Add Product");
        System.out.println("5. Update Product");
        System.out.println("6. Delete Product");
        System.out.println("7. Sell Product");
        System.out.println("8. Search Product");
        System.out.println("9. Low Stock Products");
        System.out.println("10. Sales Report");
        System.out.println("11. Logout");

        int choice = getInt("Choose: ");

        switch (choice) {
            case 1 -> productService.viewProducts();
            case 2 -> {
                System.out.println("-------------------------------------------");
                System.out.println("Categories");
                adminService.viewCategories();
                System.out.println("-------------------------------------------");
                System.out.println("If you want to exit enter 'b'");
                String name = getString("Enter new Category Name: ");
                if (name.equals(back)){
                    System.out.println("-------------------------------------------");
                    System.out.println("Back to AdminMenu..");
                    adminMenu();
                }else {
                    adminService.createCategory(name);
                    System.out.println("-------------------------------------------");
                }
            }
            case 3 -> {
                adminService.viewCategories();
                System.out.println("If you want to exit enter '0'");
                int cid = getInt("Enter Category ID to add SubCategory: ");
                if (cid == intback){
                    System.out.println("-------------------------------------------");
                    System.out.println("Back to AdminMenu...");
                    adminMenu();
                }
                else {
                    String name = getString("Enter new SubCategory Name: ");
                    adminService.createSubCategory(cid, name);
                }
            }
            case 4 -> {
                productService.viewProducts();
                addProductFlow();
            }
            case 5 -> {
                productService.viewProducts();
                updateProductFlow();
            }
            case 6 -> {
                productService.viewProducts();
                deleteProductFlow();
            }
            case 7 -> {
                productService.viewProducts();
                sellProductFlow();
            }
            case 8 -> searchProductFlow();
            case 9 -> productService.lowStock();
            case 10 -> productService.salesReport();
            case 11 -> {
                System.out.println("Logging out...");
                login();
            }
            default -> System.out.println("Invalid choice!");
        }

        return false;
    }

    // ================= STAFF MENU =================
    private boolean staffMenu() {
        System.out.println("\n=== STAFF MENU ===");
        System.out.println("1. View Products");
        System.out.println("2. Sell Product");
        System.out.println("3. Search Product");
        System.out.println("4. Report Low Stock");
        System.out.println("5. Logout");

        int choice = getInt("Choose: ");

        switch (choice) {
            case 1 -> productService.viewProducts();
            case 2 -> sellProductFlow();
            case 3 -> searchProductFlow();
            case 4 -> productService.lowStock();
            case 5 -> {
                System.out.println("Logging out...");
                return true;
            }
            default -> System.out.println("Invalid choice!");
        }
        return false;
    }

    // ================= ADD PRODUCT =================
    private void addProductFlow() {
        String name = getString("Product Name: ");
        int qty = getInt("Quantity: ");
        double cost = getDouble("Cost Price: ");
        double sell = getDouble("Selling Price: ");

        System.out.println("\nAvailable Categories:");
        adminService.viewCategories();
        int cid = getInt("Category ID: ");

        System.out.println("\nAvailable SubCategories:");
        adminService.viewSubCategories(cid);
        int sid = getInt("SubCategory ID: ");

        Category c = categoryRepo.findCategoryById(cid);
        SubCategory s = categoryRepo.findSubCategoryById(c, sid);

        if (c == null || s == null) {
            System.out.println("Invalid category/subcategory!");
            return;
        }

        productService.addProduct(name, qty, cost, sell, c, s);
    }

    private void updateProductFlow() {
        int id = getInt("Product ID to update: ");
        String name = getString("New Name: ");
        double cost = getDouble("New Cost Price: ");
        double sell = getDouble("New Selling Price: ");
        productService.updateProduct(id, name, cost, sell);
    }

    private void deleteProductFlow() {
        int id = getInt("Product ID to delete: ");
        productService.deleteProduct(id);
    }

    private void sellProductFlow() {
        int id = getInt("Product ID: ");
        int qty = getInt("Quantity: ");
        productService.sell(id, qty);
    }

    private void searchProductFlow() {
        String keyword = getString("Enter Product ID or Name to search: ");
        productService.search(keyword);
    }

    // ================= SAFE INPUT =================
    private int getInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number!");
            }
        }
    }

    private double getDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number!");
            }
        }
    }

    private String getString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}