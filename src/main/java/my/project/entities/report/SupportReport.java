package my.project.entities.report;

import java.util.Date;

public class SupportReport {
    private int id;
    private String customer;
    private String device;
    private String category;
    private String technical;
    private Date starDate;
    private Date endDate;
    private double amount;

    public SupportReport(int id, String customer, String device, String category, String technical, Date starDate, Date endDate, double amount) {
        this.id = id;
        this.customer = customer;
        this.device = device;
        this.category = category;
        this.technical = technical;
        this.starDate = starDate;
        this.endDate = endDate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getDevice() {
        return device;
    }

    public String getCategory() {
        return category;
    }

    public String getTechnical() {
        return technical;
    }

    public Date getStarDate() {
        return starDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getAmount() {
        return amount;
    }
}
