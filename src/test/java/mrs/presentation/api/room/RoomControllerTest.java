package mrs.presentation.api.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRooms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DisplayName("API 会議室一覧")
public class RoomControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    RoomController controller;

    @Mock
    ReservationCoordinator mockReservationCoordinator;

    @BeforeEach
    void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 会議室一覧を取得する() throws Exception {
        ReservableRoom reservableRoom = new ReservableRoom();
        List<ReservableRoom> list = new ArrayList<>();
        list.add(reservableRoom);
        ReservableRooms reservableRooms = new ReservableRooms(list);
        given(mockReservationCoordinator.searchReservableRooms(any())).willReturn(reservableRooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }
}