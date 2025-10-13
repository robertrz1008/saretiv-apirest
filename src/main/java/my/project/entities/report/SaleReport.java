package my.project.entities.report;

import java.util.Date;

public class SaleReport {
    private int id;
    private String product;
    private int amount;
    private String category;
    private Date date;
    private double price;
    private double subtotal;

    public SaleReport(int id, String product, int amount, String category, Date date, double price, double subtotal) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.price = price;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getPrice() {
        return price;
    }
}
