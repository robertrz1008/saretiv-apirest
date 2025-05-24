package my.project.entities.abm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
    @NotNull
    private String lastname;
    @NonNull
    private String telephone;
    @Column(nullable = false, unique = true)
    private String document;

    public Person(String name, String lastname, String phone, String doc) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.telephone = phone;
        this.document = doc;
    }
    public Person(){}

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(@NonNull String telephone) {
        this.telephone = telephone;
    }

    public @NotNull String getLastname() {
        return lastname;
    }

    public void setLastname(@NotNull String lastname) {
        this.lastname = lastname;
    }

    @NonNull
    public String getDocument() {
        return document;
    }

    public void setDocument(@NonNull String document) {
        this.document = document;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
