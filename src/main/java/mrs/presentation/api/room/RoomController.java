package mrs.presentation.api.room;

import mrs.domain.model.reservation.ReservableRooms;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API 会議室一覧
 */
@RestController("会議室一覧API")
@RequestMapping("/api/rooms")
public class RoomController {
    @GetMapping
    ReservableRooms listRooms() {
        return null;
    }
}
