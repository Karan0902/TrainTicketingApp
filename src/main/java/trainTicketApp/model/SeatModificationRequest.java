package trainTicketApp.model;

import lombok.Data;

@Data
public class SeatModificationRequest {
    private String section;
    private int seatNumber;
}
