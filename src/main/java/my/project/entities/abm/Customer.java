package my.project.entities.abm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import my.project.entities.transaction.Support;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @ColumnDefault("true")
    private Boolean status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Support> support;

    public Customer(String name, String lastname, String phone, String doc, String address, Boolean status) {
        super(name, lastname, phone, doc);
        this.address = address;
        this.status = status;
    }

    public Customer(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String toString() {
        return super.toString();
    }
}
