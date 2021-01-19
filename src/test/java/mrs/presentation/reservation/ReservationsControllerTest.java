package mrs.presentation.reservation;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.Reservations;
import mrs.domain.model.room.MeetingRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("予約画面")
public class ReservationsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ReservationsController controller;

    @Mock
    ReservationCoordinator mockReservationCoordinator;

    @BeforeEach
    public void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 予約を表示する() throws Exception {
        LocalDate today = LocalDate.now();
        when(mockReservationCoordinator.searchMeetingRoom(any())).thenReturn(new MeetingRoom());
        List<Reservation> result = new ArrayList<>(Collections.singleton(new Reservation()));
        when(mockReservationCoordinator.searchReservations(any())).thenReturn(new Reservations(result));

        mockMvc.perform(get("/reservations/" + today.toString() + "/1"))
                .andExpect(status().isOk());
    }

}
