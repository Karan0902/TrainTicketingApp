package trainTicketApp.model;

import lombok.Data;

@Data
public class Receipt {
    private Ticket ticket;
    private SeatAllocation seatAllocation;

    public Receipt(Ticket ticket, SeatAllocation seatAllocation) {
        this.ticket = ticket;
        this.seatAllocation = seatAllocation;
    }

}
