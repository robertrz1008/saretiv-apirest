package my.project.entities.transaction;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.project.entities.abm.CategoryDevice;

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

    public TypeSupport(String description, double amount, CategoryDevice category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
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
}
