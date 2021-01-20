package mrs.presentation.api.room;

import mrs.application.coordinator.reservation.ReservationCoordinator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.fail;

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
        fail("WIP");
    }
}
