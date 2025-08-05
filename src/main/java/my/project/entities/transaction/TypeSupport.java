package my.project.entities.transaction;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.project.entities.abm.CategoryDevice;

import java.util.List;

@Entity
@Table(name = "types_support")
public class TypeSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String description;
    @NotNull
    private double amount;

    @ManyToOne
    @JoinColumn(name= "catdevice", referencedColumnName = "id", nullable = false)
    private CategoryDevice category;

    @ManyToMany
    @JoinTable(name="support_detail", joinColumns = @JoinColumn(name = "support_id"), inverseJoinColumns = @JoinColumn(name = "typesupport_id"))
    private List<Support> supports;

    public TypeSupport(String description, double amount, CategoryDevice category, List<Support> supports) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.supports = supports;
    }
    public TypeSupport(){}

    public int getId() {
        return id;
    }


    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @NotNull
    public double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull double amount) {
        this.amount = amount;
    }

    public CategoryDevice getCategory() {
        return category;
    }

    public void setCategory(CategoryDevice category) {
        this.category = category;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }
}
