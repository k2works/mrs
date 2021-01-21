package mrs.presentation.api.reservation;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("API 会議室予約")
public class ReservationsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    ReservationsController controller;

    @Mock
    ReservationCoordinator mockReservationCoordinator;

    @BeforeEach
    void setUpMockMvc() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 会議室を予約する() throws Exception {
        doNothing().when(mockReservationCoordinator).reserveMeetingRoom(null, null, null);

        mockMvc.perform(
                post("/api/reservations/2021-01-01/1")
                        .param("start", String.valueOf(LocalTime.of(10, 0)))
                        .param("end", String.valueOf(LocalTime.of(10, 30)))
        ).andExpect(status().isOk());

    }

    @Test
    void 会議室の予約をキャンセルする() {
        fail();
    }
}
