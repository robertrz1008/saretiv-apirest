package my.project.entities.abm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import my.project.entities.transaction.Support;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Date entryDate;

    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Support> supports;

    public UserEntity(
            String name,
            String lastname,
            String phone,
            String doc,
            String username,
            String password,
            Date entryDate,
            boolean status,
            Set<Role> roles)
    {
        super(name, lastname, phone, doc);
        this.username = username;
        this.entryDate = entryDate;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public UserEntity() {
    }

    @Override
    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static class Builder{
        private String name;
        private String lastname;
        private String document;
        private String username;
        private String password;
        private Date entryDate;
        private boolean status;
        private String telephone;
        private Set<Role> roles;

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder phone(String phone) {
            this.telephone = phone;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder entryDate(Date entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder status(boolean status) {
            this.status = status;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }
        public Builder addOneRole(int id, String name){
            Role thisRole = new Role(name);
            thisRole.setId(id);

            Set<Role> rolesList = Set.of(thisRole);
            this.roles = rolesList;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(
                    name,
                    lastname,
                    telephone,
                    document,
                    username,
                    password,
                    entryDate,
                    status,
                    roles
            );
        }

    }
}