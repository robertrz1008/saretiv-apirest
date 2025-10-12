package my.project.entities.report;

public class Customer {
    private Long id;
    private String name;
    private String telephone;
    private String address;
    private String document;
    private String status;

    public Customer(Long id, String name, String telephone, String address, String document, String status) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.document = document;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public String getDocument() {
        return document;
    }
}
