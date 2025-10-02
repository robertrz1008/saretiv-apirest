package my.project.entities.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import my.project.entities.abm.CategoryDevice;
import my.project.entities.abm.CategoryProduct;

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

    @OneToMany(mappedBy = "supportType", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SupportActivity> supportActivities;

    public TypeSupport(String description, double amount, CategoryDevice category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    public TypeSupport(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static class Builder{
        private int id;
        private String description;
        private double amount;
        private CategoryDevice category;

        public Builder id(int id){
            this.id = id;
            return this;
        }
        public Builder description(String description){
            this.description = description;
            return this;
        }
        public Builder amount(double amount){
            this.amount = amount;
            return this;
        }
        public Builder categoryDevice( int id, String name){
            this.category = new CategoryDevice(name);
            category.setId(id);
            return this;
        }

        public TypeSupport build(){
            TypeSupport sup = new TypeSupport(description, amount, category);
            sup.setId(id);

            return sup;
        }
    }

}
