import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class TrainTicketApplication {

    private List<User> users = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<SeatAllocation> seatAllocations = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(TrainTicketApplication.class, args);
    }

    @PostMapping("/purchase_ticket")
    public Receipt purchaseTicket(@RequestBody PurchaseRequest purchaseRequest) {
        // Assume ticket price is fixed
        double pricePaid = 20;

        // Dummy seat allocation logic: Assign seats alternately between sections A and B
        String section = seatAllocations.size() % 2 == 0 ? "A" : "B";
        int seatNumber = seatAllocations.size() / 2 + 1;

        User user = new User(purchaseRequest.getFirstName(), purchaseRequest.getLastName(), purchaseRequest.getEmail());
        users.add(user);

        Ticket ticket = new Ticket(user, purchaseRequest.getFromLocation(), purchaseRequest.getToLocation(), pricePaid);
        tickets.add(ticket);

        SeatAllocation seatAllocation = new SeatAllocation(user, section, seatNumber);
        seatAllocations.add(seatAllocation);

        return new Receipt(ticket, seatAllocation);
    }

    @GetMapping("/receipt/{userId}")
    public Receipt getReceiptDetails(@PathVariable long userId) {
        User user = findUserById(userId);
        if (user == null) {
            return null; // Handle user not found
        }
        Ticket ticket = findTicketByUser(user);
        if (ticket == null) {
            return null; // Handle ticket not found
        }
        SeatAllocation seatAllocation = findSeatAllocationByUser(user);
        if (seatAllocation == null) {
            return null; // Handle seat allocation not found
        }
        return new Receipt(ticket, seatAllocation);
    }

    @GetMapping("/seat_allocations/{section}")
    public List<SeatAllocation> getSeatAllocationsBySection(@PathVariable String section) {
        List<SeatAllocation> sectionSeatAllocations = new ArrayList<>();
        for (SeatAllocation seatAllocation : seatAllocations) {
            if (seatAllocation.getSection().equalsIgnoreCase(section)) {
                sectionSeatAllocations.add(seatAllocation);
            }
        }
        return sectionSeatAllocations;
    }

    @DeleteMapping("/remove_user/{userId}")
    public String removeUserFromTrain(@PathVariable long userId) {
        User user = findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        users.remove(user);
        Ticket ticket = findTicketByUser(user);
        if (ticket != null) {
            tickets.remove(ticket);
        }
        SeatAllocation seatAllocation = findSeatAllocationByUser(user);
        if (seatAllocation != null) {
            seatAllocations.remove(seatAllocation);
        }
        return "User removed from train";
    }

    @PutMapping("/modify_seat/{userId}")
    public String modifyUserSeat(@PathVariable long userId, @RequestBody SeatModificationRequest seatModificationRequest) {
        User user = findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        SeatAllocation seatAllocation = findSeatAllocationByUser(user);
        if (seatAllocation == null) {
            return "Seat allocation not found";
        }
        seatAllocation.setSection(seatModificationRequest.getSection());
        seatAllocation.setSeatNumber(seatModificationRequest.getSeatNumber());
        return "User's seat modified successfully";
    }

    private User findUserById(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    private Ticket findTicketByUser(User user) {
        for (Ticket ticket : tickets) {
            if (ticket.getUser().equals(user)) {
                return ticket;
            }
        }
        return null;
    }

    private SeatAllocation findSeatAllocationByUser(User user) {
        for (SeatAllocation seatAllocation : seatAllocations) {
            if (seatAllocation.getUser().equals(user)) {
                return seatAllocation;
            }
        }
        return null;
    }
}

class User {
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

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

class Ticket {
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

class SeatAllocation {
    private static long idCounter = 1;
    private long id;
    private User user;
    private String section;
    private int seatNumber;

    public SeatAllocation(User user, String section, int seatNumber) {
        this.id = idCounter++;
        this.user = user;
        this.section = section;
        this.seatNumber = seatNumber;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}

class PurchaseRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String fromLocation;
    private String toLocation;

    // Getters and setters
}

class Receipt {
    private Ticket ticket;
    private SeatAllocation seatAllocation;

    // Constructors, getters, and setters
}

class SeatModificationRequest {
    private String section;
    private int seatNumber;

    // Getters and setters
}
