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

    //  LOGIN
    private User login() {
        while (true) {
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println("     LOGIN (Admin or Staff) | (e = Exit)   ");
            System.out.println("-------------------------------------------");
            System.out.print("Username: ");
            String u = sc.nextLine();

            if (u.equalsIgnoreCase("e")) {
                System.out.println("Exiting program...");
                System.exit(0);
            }

            System.out.print("Password: ");
            String p = sc.nextLine();

            if (p.equalsIgnoreCase("e")) {
                System.out.println("Exiting program...");
                System.exit(0);
            }

            User user = userRepo.authenticate(u, p);

            if (user != null) {
                System.out.println("Welcome " + user.getUsername()
                        + " | Role: " + user.getRole());
                return user;
            }

            System.out.println("Invalid credentials!");
        }
    }

    // ADMIN MENU
    private boolean adminMenu() {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("                 ADMIN MENU                ");
        System.out.println("-------------------------------------------");
        System.out.println("1. View Products");
        System.out.println("2. Create Category");
        System.out.println("3. Create SubCategory");
        System.out.println("4. Add Product");
        System.out.println("5. Update Product");
        System.out.println("6. Delete Product");
        System.out.println("7. Update Category");
        System.out.println("8. Delete Category");
        System.out.println("9. Update SubCategory");
        System.out.println("10. Delete SubCategory");
        System.out.println("11. Sell Product");
        System.out.println("12. Search Product");
        System.out.println("13. Low Stock Products");
        System.out.println("14. Sales Report");
        System.out.println("15. Logout");
        System.out.println("-------------------------------------------");

        int choice = getInt("Choose: ");

        switch (choice) {
            case 1 -> productService.viewProducts();
            case 2 -> {
                adminService.viewCategories();
                System.out.println("If you want to exit enter 'b'");
                String name = getString("Enter new Category Name: ");
                if (name.equals(back)){
                    Back();
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
                    Back();
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
            case 7 -> updateCategoryFlow();
            case 8 -> deleteCategoryFlow();
            case 9 -> updateSubCategoryFlow();
            case 10 -> deleteSubCategoryFlow();
            case 11 -> {
                productService.viewProducts();
                sellProductFlow();
            }
            case 12 -> searchProductFlow();
            case 13 -> productService.lowStock();
            case 14 -> productService.salesReport();
            case 15 -> {
                System.out.println("Logging out...");
                return true;
            }
            default -> System.out.println("Invalid choice!");
        }

        return false;
    }
    //  STAFF MENU
    private boolean staffMenu() {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("                 STAFF MENU                ");
        System.out.println("-------------------------------------------");
        System.out.println("1. View Products");
        System.out.println("2. Sell Product");
        System.out.println("3. Search Product");
        System.out.println("4. Report Low Stock");
        System.out.println("5. Logout");
        System.out.println("-------------------------------------------");

        int choice = getInt("Choose: ");

        switch (choice) {
            case 1 -> productService.viewProducts();
            case 2 -> sellProductFlowStaff();
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

    // Add Item
    private void addProductFlow() {
        System.out.println("If you want to exit enter 'b'");
        String name = getString("Product Name: ");
        if (name.equals(back)) {
            Back();
        } else {
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
    }

    // Sell Item
    private void sellProductFlow() {
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Product ID: ");
        if (id == intback) {
            Back();
        } else {
            int qty;

            while (true) {
                qty = getInt("Quantity: ");
                if (qty > 0) break;
                System.out.println("Quantity must be greater than 0!");
            }

            productService.sell(id, qty);
        }
    }
    private void sellProductFlowStaff() {
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Product ID: ");
        if (id == intback) {
            Back();
        } else {
            int qty;

            while (true) {
                qty = getInt("Quantity: ");
                if (qty > 0) break;
                System.out.println("Quantity must be greater than 0!");
            }

            productService.sell(id, qty);
        }
    }

    private void searchProductFlow() {
        System.out.println("If you want to exit enter 'b' or '0'");
        String keyword = getString("Enter Product ID or Name to search: ");
        if (keyword.equals(back) || keyword.equals("0")) {
            Back();
        } else {
            productService.search(keyword);
        }
    }
    // Update
    private void updateProductFlow() {
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Product ID to update: ");
        if (id == intback) {
            Back();
        } else {
            String name = getString("New Name: ");
            double cost = getDouble("New Cost Price: ");
            double sell = getDouble("New Selling Price: ");
            productService.updateProduct(id, name, cost, sell);
        }
    }

    private void deleteProductFlow() {
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Product ID to delete: ");
        if (id == intback) {
            Back();
        } else {
            productService.deleteProduct(id);
        }
    }
    private void updateCategoryFlow() {
        adminService.viewCategories();
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Category ID to update: ");
        if (id == intback) return;

        String name = getString("New Category Name: ");
        categoryRepo.updateCategory(id, name);
    }
    private void deleteCategoryFlow() {
        adminService.viewCategories();
        System.out.println("If you want to exit enter '0'");
        int id = getInt("Category ID to delete: ");
        if (id == intback) return;

        categoryRepo.deleteCategory(id);


    }
    private void updateSubCategoryFlow() {
        adminService.viewCategories();
        int cid = getInt("Category ID: ");
        if (cid == intback) return;

        Category c = categoryRepo.findCategoryById(cid);
        if (c == null) {
            System.out.println("Invalid Category!");
            return;
        }

        adminService.viewSubCategories(cid);
        int sid = getInt("SubCategory ID to update: ");
        if (sid == intback) return;

        String name = getString("New SubCategory Name: ");
        categoryRepo.updateSubCategory(c, sid, name);
    }
    private void deleteSubCategoryFlow() {
        adminService.viewCategories();
        int cid = getInt("Category ID: ");
        if (cid == intback) return;

        Category c = categoryRepo.findCategoryById(cid);
        if (c == null) {
            System.out.println("Invalid Category!");
            return;
        }

        adminService.viewSubCategories(cid);
        int sid = getInt("SubCategory ID to delete: ");
        if (sid == intback) return;

        categoryRepo.deleteSubCategory(c, sid);
        System.out.println("SubCategory deleted successfully!");
    }


    //  SAFE INPUT
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
    private void Back(){
        System.out.println("-------------------------------------------");
        System.out.println("Back to AdminMenu....");
        System.out.println();
        adminMenu();
    }
    private void BackStaff(){
        System.out.println("-------------------------------------------");
        System.out.println("Back to StaffMenu....");
        System.out.println();
        staffMenu();
    }
}