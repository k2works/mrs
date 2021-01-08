package mrs.domain.model.reservation;

import java.util.List;

/**
 * 予約集合
 */
public class Reservations {
    List<Reservation> value;

    public Reservations(List<Reservation> value) {
        this.value = value;
    }

    public List<Reservation> value() {
        return value;
    }
}
