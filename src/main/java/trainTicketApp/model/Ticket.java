package trainTicketApp.model;


import lombok.Data;

@Data
public class Ticket {
    private static long idCounter = 1;
    private long id;
    private User user;
    private String fromLocation;
    private String toLocation;
    private double pricePaid;

    public Ticket(User user, String fromLocation, String toLocation, double pricePaid) {
        this.id = idCounter++;
        this.user = user;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.pricePaid = pricePaid;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public double getPricePaid() {
        return pricePaid;
    }
}
