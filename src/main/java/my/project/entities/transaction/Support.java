package my.project.entities.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import my.project.entities.abm.Customer;
import my.project.entities.abm.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "supports")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date startDate;
    private Date endDate;
    private String status;
    private double total;

    @OneToOne(mappedBy = "support")
    @JsonIgnore
    private Device device;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "support", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductDetail> productDetails;

    @OneToMany(mappedBy = "support", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SupportActivity> supportActivities;

    public Support(Date startDate, Date endDate, String status, double total, Device device, Customer customer, UserEntity user) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.total = total;
        this.device = device;
        this.customer = customer;
    }
    public Support(){}

    public long getId() {
        return id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Support{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
