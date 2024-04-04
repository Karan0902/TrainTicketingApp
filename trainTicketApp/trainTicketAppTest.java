import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TrainTicketApplication.class)
public class TrainTicketApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainTicketApplication trainTicketApplication;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testPurchaseTicket() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest("John", "Doe", "john.doe@example.com", "London", "France");
        Receipt receipt = new Receipt(new Ticket(new User("John", "Doe", "john.doe@example.com"), "London", "France", 20),
                new SeatAllocation(new User("John", "Doe", "john.doe@example.com"), "A", 1));

        when(trainTicketApplication.purchaseTicket(any())).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.post("/purchase_ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket.user.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatAllocation.section").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatAllocation.seatNumber").value(1));
    }

    @Test
    void testGetReceiptDetails() throws Exception {
        User user = new User("John", "Doe", "john.doe@example.com");
        Ticket ticket = new Ticket(user, "London", "France", 20);
        SeatAllocation seatAllocation = new SeatAllocation(user, "A", 1);
        Receipt receipt = new Receipt(ticket, seatAllocation);

        when(trainTicketApplication.getReceiptDetails(anyLong())).thenReturn(receipt);

        mockMvc.perform(MockMvcRequestBuilders.get("/receipt/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ticket.user.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatAllocation.section").value("A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seatAllocation.seatNumber").value(1));
    }

    // Similar tests for other endpoints
}
