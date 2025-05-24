package my.project.entities.abm;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

public class Client extends Person {
    private String direction;
    private boolean status;
    @NonNull
    private String name;
    @NotNull
    private String lastname;
    @NonNull
    private String telephone;
    @NonNull
    private String document;

    public Client(String name, String lastname, String phone, String doc, String direction, boolean status) {
        super(name, lastname, phone, doc);
        this.direction = direction;
        this.status = status;
    }

    public static void main(String[] args) {
        Client cli = new Client("fd","fdf", "fd", "fdfd", "ffd", false);

        System.out.println(cli.getName());
    }

}
