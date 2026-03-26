package service;

import model.Category;
import model.Product;
import model.Sale;
import model.SubCategory;
import repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private ProductRepository repo;
    private List<Sale> sales = new ArrayList<>();

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ADD PRODUCT
    public void addProduct(String name, int qty, double cost, double sell, Category c, SubCategory s) {
        int id = repo.generateId();
        Product p = new Product(id, name, qty, cost, sell, c, s);
        repo.add(p);
        System.out.println("----------------------------------------");
        System.out.println("Product added successfully!");
        System.out.println("----------------------------------------");
    }

    // VIEW PRODUCTS
    public void viewProducts() {
        System.out.printf("\n%-5s %-20s %-5s %-10s %-15s %-15s%n",
                "ID", "Name", "Qty", "Sell", "Category", "SubCategory");
        System.out.println("--------------------------------------------------------------------------------");

        for (Product p : repo.getAll()) {
            System.out.printf("%-5d %-20s %-5d $%-9.2f %-15s %-15s%n",
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getSellingPrice(),
                    p.getCategory().getName(),
                    p.getSubCategory().getName());
            System.out.println("--------------------------------------------------------------------------------");
        }
    }

    // UPDATE PRODUCT
    public void updateProduct(int id, String name, double cost, double sell) {

        Product p = repo.findById(id);
        if (p == null) {
            System.out.println("----------------------------------------");
            System.out.println("Product not found!");
            System.out.println("----------------------------------------");
            return;
        }
        p.update(name, cost, sell);
        System.out.println("----------------------------------------");
        System.out.println("Product updated successfully!");
        System.out.println("----------------------------------------");
    }

    // DELETE PRODUCT
    public void deleteProduct(int id) {
        Product p = repo.findById(id);
        if (p != null) {
            repo.delete(id);
            System.out.printf("Deleted Product -> ID: %d | Name: %s%n", p.getId(), p.getName());
        } else {
            System.out.println("----------------------------------------");
            System.out.println("Product not found!");
            System.out.println("----------------------------------------");
        }
    }

    // SEARCH PRODUCT (ID or NAME)
    public void search(String keyword) {
        boolean found = false;
        System.out.println("\nSearch Results:");
        for (Product p : repo.getAll()) {
            if (String.valueOf(p.getId()).equals(keyword) ||
                    p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(p.getId() + " | " + p.getName()
                        + " | Qty: " + p.getQuantity()
                        + " | Sell: " + p.getSellingPrice());
                found = true;
            }
        }
        if (!found) {
            System.out.println("----------------------------------------");
            System.out.println("No product found!");
            System.out.println("----------------------------------------");
        }
    }

    // SELL PRODUCT
    public void sell(int id, int qty) {

        if (qty <= 0) {
            System.out.println("Quantity must be greater than 0!");
            return;
        }

        Product p = repo.findById(id);
        if (p == null) {
            System.out.println("----------------------------------------");
            System.out.println("Product not found!");
            System.out.println("----------------------------------------");
            return;
        }

        if (p.getQuantity() < qty) {
            System.out.println("----------------------------------------");
            System.out.println("Not enough item!");
            System.out.println("----------------------------------------");
            return;
        }

        p.decreaseQuantity(qty);

        Sale s = new Sale(p, qty);
        sales.add(s);

        System.out.printf("\n%-20s %-5s %-10s%n", "Product", "Qty", "Total");
        System.out.println("-----------------------------------------");

        System.out.printf("%-20s %-5d $%-9.2f%n",
                p.getName(), qty, s.getProfit());

        System.out.println("-----------------------------------------");
        System.out.println("Thank you for supporting our business!");
    }
    // SALES REPORT
    public void salesReport() {
        double totalProfit = 0;
        double totalOriginalPrice = 0;

        System.out.printf("\n%-20s %-5s %-15s %-10s %-20s%n",
                "Product", "Qty", "Original Price", "Profit", "Date");
        for (Sale s : sales) {
            String productName = s.getProduct().getName();
            int qty = s.getQty();
            double originalPrice = s.getProduct().getCostPrice() * qty;
            double profit = s.getProfit();
            String date = s.getDate().toString();

            System.out.printf("%-20s %-5d $%-14.2f $%-9.2f %-20s%n",
                    productName, qty, originalPrice, profit, date);

            totalProfit += profit;
            totalOriginalPrice += originalPrice;
        }

        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-20s %-5s $%-14.2f $%-9.2f%n",
                "TOTAL", "", totalOriginalPrice, totalProfit);
        System.out.println("---------------------------------------------------------------------");
    }

    // LOW STOCK PRODUCTS
    public void lowStock() {
        boolean found = false;
        System.out.println("\nLow Stock Products ");
        for (Product p : repo.getAll()) {
            if (p.getQuantity() <= 5) {
                System.out.println("----------------------------------------");
                System.out.println(p.getId() + " | " + p.getName()
                        + " | Qty: " + p.getQuantity());
                found = true;

            }
        }

        if (!found){
            System.out.println("----------------------------------------");
            System.out.println("No low stock products.");
            System.out.println("----------------------------------------");
        }

    }
}