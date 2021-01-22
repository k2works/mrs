package mrs.presentation.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRooms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("会議室一覧画面")
public class RoomsControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    RoomsController controller;

    @Mock
    ReservationCoordinator mockReservationCoordinator;

    @BeforeEach
    public void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 会議室一覧を表示する() throws Exception {
        List<ReservableRoom> result = new ArrayList<>(Collections.singleton(new ReservableRoom()));
        when(mockReservationCoordinator.searchReservableRooms(any())).thenReturn(new ReservableRooms(result));

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk());
    }

}
