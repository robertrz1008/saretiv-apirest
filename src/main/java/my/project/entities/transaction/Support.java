package my.project.entities.transaction;

import jakarta.persistence.*;
import my.project.entities.abm.Customer;

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
    private Device device;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(name="support_detail", joinColumns = @JoinColumn(name = "support_id"), inverseJoinColumns = @JoinColumn(name = "typesupport_id"))
    private List<TypeSupport> typeSupports;

    public Support(Date startDate, Date endDate, String status, double total, Device device, Customer customer, List<TypeSupport> typeSupports) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.total = total;
        this.device = device;
        this.customer = customer;
        this.typeSupports = typeSupports;
    }

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

    public List<TypeSupport> getTypeSupports() {
        return typeSupports;
    }

    public void setTypeSupports(List<TypeSupport> typeSupports) {
        this.typeSupports = typeSupports;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
