package trainTicketApp.service;

import org.springframework.stereotype.Service;
import trainTicketApp.model.SeatAllocation;
import trainTicketApp.model.Ticket;
import trainTicketApp.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainTicketService {


    private List<User> users = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<SeatAllocation> seatAllocations = new ArrayList<>();

    public TrainTicketService() {
        this.users = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.seatAllocations = new ArrayList<>();
    }

    public User findUserById(long userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public Ticket findTicketByUser(User user) {
        for (Ticket ticket : tickets) {
            if (ticket.getUser().equals(user)) {
                return ticket;
            }
        }
        return null;
    }

    public SeatAllocation findSeatAllocationByUser(User user) {
        for (SeatAllocation seatAllocation : seatAllocations) {
            if (seatAllocation.getUser().equals(user)) {
                return seatAllocation;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public List<SeatAllocation> getSeatAllocations() {
        return this.seatAllocations;
    }
}
