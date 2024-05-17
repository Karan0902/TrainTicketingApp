package trainTicketApp.model;

import lombok.Data;

@Data
public class SeatAllocation {
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

}
