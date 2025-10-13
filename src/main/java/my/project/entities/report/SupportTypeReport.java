package my.project.entities.report;

public class SupportTypeReport {
    private int id;
    private String description;
    private double amount;
    private String category;

    public SupportTypeReport(int id, String description, double amount, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }


}
