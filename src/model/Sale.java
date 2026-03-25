package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {
    private Product product;
    private int qty;
    private double profit;
    private String date;

    public Sale(Product product, int qty) {
        this.product = product;
        this.qty = qty;
        this.profit = (product.getSellingPrice() - product.getCostPrice()) * qty;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public Product getProduct() { return product; }
    public int getQty() { return qty; }
    public double getProfit() { return profit; }
    public String getDate() { return date; }
}