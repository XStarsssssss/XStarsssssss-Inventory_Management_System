package model;

public class Product extends Item {
    private int quantity;
    private double costPrice;
    private double sellingPrice;
    private Category category;
    private SubCategory subCategory;

    public Product(int id, String name, int quantity,
                   double costPrice, double sellingPrice,
                   Category category, SubCategory subCategory) {

        super(id, name);
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.category = category;
        this.subCategory = subCategory;
    }

    public int getQuantity() { return quantity; }
    public void increaseQuantity(int qty) { quantity += qty; }
    public void decreaseQuantity(int qty) { quantity -= qty; }

    public double getCostPrice() { return costPrice; }
    public double getSellingPrice() { return sellingPrice; }

    public Category getCategory() { return category; }
    public SubCategory getSubCategory() { return subCategory; }

    public void update(String name, double cost, double sell) {
        this.name = name;
        this.costPrice = cost;
        this.sellingPrice = sell;
    }
}