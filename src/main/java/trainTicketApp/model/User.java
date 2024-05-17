package trainTicketApp.model;

import lombok.Data;

@Data
public class User {
    private static long idCounter = 1;
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public User(String firstName, String lastName, String email) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
