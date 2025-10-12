package my.project.entities.report;

public class ProductReport {
    private int id;
    private String barcode;
    private String description;
    private String category;
    private String supplier;
    private double priceBuy;
    private double priceSale;

    public ProductReport(int id, String barcode, String description, String category, String supplier, double priceBuy, double priceSale) {
        this.id = id;
        this.barcode = barcode;
        this.description = description;
        this.category = category;
        this.supplier = supplier;
        this.priceBuy = priceBuy;
        this.priceSale = priceSale;
    }

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSupplier() {
        return supplier;
    }

    public double getPriceBuy() {
        return priceBuy;
    }

    public double getPriceSale() {
        return priceSale;
    }
}
