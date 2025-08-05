package my.project.entities.abm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import my.project.entities.transaction.Device;



import my.project.entities.transaction.TypeSupport;

import java.util.List;

@Entity
@Table(name = "categories_device")
public class CategoryDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TypeSupport> typeSupports;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Device> devices;



    public CategoryDevice(String name) {
        this.name = name;
    }
    public CategoryDevice(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
