package my.project.entities.transaction;

import jakarta.persistence.*;
import my.project.entities.abm.CategoryDevice;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String observation;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name= "catdevice_id", referencedColumnName = "id", nullable = false)
    private CategoryDevice category;

    @OneToOne
    @JoinColumn(name= "support_id", referencedColumnName = "id", nullable = false)
    private Support support;

    public Device(String observation, String description, CategoryDevice category, Support support) {
        this.observation = observation;
        this.description = description;
        this.category = category;
        this.support = support;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDevice getCategory() {
        return category;
    }

    public void setCategory(CategoryDevice category) {
        this.category = category;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }
}
