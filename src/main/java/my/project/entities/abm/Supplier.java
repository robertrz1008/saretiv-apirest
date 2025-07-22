package my.project.entities.abm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String name;
    private String telephone;
    private String ruc;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;

    public Supplier(String name, String telephone, String ruc) {
        this.name = name;
        this.telephone = telephone;
        this.ruc = ruc;
    }

    public Supplier(){}

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public void setName(String name) {
        this.name = name;
    }
}
