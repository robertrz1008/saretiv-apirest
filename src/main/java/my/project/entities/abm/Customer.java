package my.project.entities.abm;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "customers")
public class Customer extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;
    @ColumnDefault("true")
    private Boolean status;

    public Customer(String name, String lastname, String phone, String doc, String address, Boolean status) {
        super(name, lastname, phone, doc);
        this.address = address;
        this.status = status;
    }

    public Customer(){}

    public long getId() {
        return id;
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
