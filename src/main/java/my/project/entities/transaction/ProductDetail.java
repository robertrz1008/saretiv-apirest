package my.project.entities.transaction;

import jakarta.persistence.*;
import my.project.entities.abm.Product;

@Entity
@Table(name = "products_detail")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int ProductAmount;

    @Column(nullable = false)
    private int subtotal;


    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "support_id", referencedColumnName = "id", nullable = true)
    private Support support;

    public ProductDetail(int productAmount, int subtotal, Product product, Sale sale, Support support) {
        this.ProductAmount = productAmount;
        this.subtotal = subtotal;
        this.product = product;
        this.sale = sale;
        this.support = support;
    }

    public ProductDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(int productAmount) {
        ProductAmount = productAmount;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
