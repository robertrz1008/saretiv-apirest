package my.project.entities.abm;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String description;
    @Column(name = "entry_price")
    private double entryPrice;
    @Column(name = "entry_sale")
    private double salePrice;
    private int stock;
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private CategoryProduct category;

    public Product(int id, String description, double entryPrice, double salePrice, int stock, Supplier supplier, CategoryProduct category, String barcode) {
        this.id = id;
        this.description = description;
        this.entryPrice = entryPrice;
        this.salePrice = salePrice;
        this.stock = stock;
        this.supplier = supplier;
        this.category = category;
        this.barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
