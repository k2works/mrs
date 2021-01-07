package mrs.presentation.reservation;

import mrs.application.service.reservation.ReservationService;
import mrs.application.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("予約画面")
public class ReservationsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ReservationsController controller;

    @Mock
    RoomService mockRoomService;

    @Mock
    ReservationService reservationService;

    @BeforeEach
    public void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    void 予約を表示する() throws Exception {
        LocalDate today = LocalDate.now();
        mockMvc.perform(get("/reservations/" + today.toString() + "/1"))
                .andExpect(status().isOk());
    }

}
