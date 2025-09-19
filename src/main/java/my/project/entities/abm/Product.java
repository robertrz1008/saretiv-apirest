package my.project.entities.abm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import my.project.entities.transaction.ProductDetail;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductDetail> productDetails;

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

    public Product(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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




    public static class Builder{
        private int id;
        private String description;
        private double entryPrice;
        private double salePrice;
        private int stock;
        private String barcode;
        private Supplier supplier;
        private CategoryProduct category;

        public Builder id(int id){
            this.id = id;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }
        public Builder entryPrice(double entryPrice){
            this.entryPrice = entryPrice;
            return this;
        }
        public Builder salePrice(double salePrice){
            this.salePrice = salePrice;
            return this;
        }
        public Builder stock(int stock){
            this.stock = stock;
            return this;
        }
        public Builder barcode(String barcode){
            this.barcode = barcode;
            return this;
        }





        public Builder supplier(
                int  id,
                String name,
                String telephone,
                String ruc,
                String address
        ){
            this.supplier = new Supplier(name, telephone, ruc, address);
            supplier.setId(id);
            return this;
        }
        public Builder categoryProduct(
                int id,
                String name
        ){
            this.category = new CategoryProduct(name);
            category.setId(id);
            return this;
        }

        public Product build(){
            return new Product(
                    id,
                    description,
                    entryPrice,
                    salePrice,
                    stock,
                    supplier,
                    category,
                    barcode

            );
        }
    }


}
