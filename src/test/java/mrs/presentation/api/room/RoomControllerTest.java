package mrs.presentation.api.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("API 会議室一覧")
public class RoomControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    RoomController controller;

    @Mock
    ReservationCoordinator mockReservationCoordinator;

    @BeforeEach
    void setUpMockMvc() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 会議室一覧を取得する() throws Exception {
        given(mockReservationCoordinator.searchReservableRooms(any())).willReturn(null);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void 日付を指定して会議室一覧を取得する() throws Exception {
        given(mockReservationCoordinator.searchReservableRooms(any())).willReturn(null);

        mockMvc.perform(
                get("/api/rooms")
                        .param("date", "20200-01-01")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
