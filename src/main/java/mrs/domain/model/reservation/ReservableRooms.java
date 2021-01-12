package mrs.domain.model.reservation;

import java.util.List;

/**
 * 予約可能会議室一覧
 */
public class ReservableRooms {
    List<ReservableRoom> value;

    public ReservableRooms(List<ReservableRoom> value) {
        this.value = value;
    }

    public List<ReservableRoom> value() {
        return value;
    }
}
