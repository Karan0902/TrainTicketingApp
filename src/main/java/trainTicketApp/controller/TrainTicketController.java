package trainTicketApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trainTicketApp.model.*;
import trainTicketApp.service.TrainTicketService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainTicketController {

    @Autowired
    TrainTicketService trainTicketService;

    @GetMapping("/")
    public String getHealth(){
        return "hello world";
    }

    @PostMapping("/purchase_ticket")
    public Receipt purchaseTicket(@RequestBody PurchaseRequest purchaseRequest) {
        // Assume ticket price is fixed
        int pricePaid = 20;

        // Dummy seat allocation logic: Assign seats alternately between sections A and B
        String section = trainTicketService.getSeatAllocations().size() % 2 == 0 ? "A" : "B";
        int seatNumber = trainTicketService.getSeatAllocations().size() / 2 + 1;

        User user = new User(purchaseRequest.getFirstName(), purchaseRequest.getLastName(), purchaseRequest.getEmail());
        trainTicketService.getUsers().add(user);

        Ticket ticket = new Ticket(user, purchaseRequest.getFromLocation(), purchaseRequest.getToLocation(), pricePaid);
        trainTicketService.getTickets().add(ticket);

        SeatAllocation seatAllocation = new SeatAllocation(user, section, seatNumber);
        trainTicketService.getSeatAllocations().add(seatAllocation);

        return new Receipt(ticket, seatAllocation);
    }

    @GetMapping("/receipt/{userId}")
    public Receipt getReceiptDetails(@PathVariable long userId) {
        User user = trainTicketService.findUserById(userId);
        if (user == null) {
            return null; // Handle user not found
        }
        Ticket ticket = trainTicketService.findTicketByUser(user);
        if (ticket == null) {
            return null; // Handle ticket not found
        }
        SeatAllocation seatAllocation = trainTicketService.findSeatAllocationByUser(user);
        if (seatAllocation == null) {
            return null; // Handle seat allocation not found
        }
        return new Receipt(ticket, seatAllocation);
    }

    @GetMapping("/seat_allocations/{section}")
    public List<SeatAllocation> getSeatAllocationsBySection(@PathVariable String section) {
        List<SeatAllocation> sectionSeatAllocations = new ArrayList<>();
        for (SeatAllocation seatAllocation : trainTicketService.getSeatAllocations()) {
            if (seatAllocation.getSection().equalsIgnoreCase(section)) {
                sectionSeatAllocations.add(seatAllocation);
            }
        }
        return sectionSeatAllocations;
    }

    @DeleteMapping("/remove_user/{userId}")
    public String removeUserFromTrain(@PathVariable long userId) {
        User user = trainTicketService.findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        trainTicketService.getUsers().remove(user);
        Ticket ticket = trainTicketService.findTicketByUser(user);
        if (ticket != null) {
            trainTicketService.getUsers().remove(ticket);
        }
        SeatAllocation seatAllocation = trainTicketService.findSeatAllocationByUser(user);
        if (seatAllocation != null) {
            trainTicketService.getSeatAllocations().remove(seatAllocation);
        }
        return "User removed from train";
    }

    @PutMapping("/modify_seat/{userId}")
    public String modifyUserSeat(@PathVariable long userId, @RequestBody SeatModificationRequest seatModificationRequest) {
        User user = trainTicketService.findUserById(userId);
        if (user == null) {
            return "User not found";
        }
        SeatAllocation seatAllocation = trainTicketService.findSeatAllocationByUser(user);
        if (seatAllocation == null) {
            return "Seat allocation not found";
        }
        seatAllocation.setSection(seatModificationRequest.getSection());
        seatAllocation.setSeatNumber(seatModificationRequest.getSeatNumber());
        return "User's seat modified successfully";
    }

}
