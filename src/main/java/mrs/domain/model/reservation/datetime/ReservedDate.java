package mrs.domain.model.reservation.datetime;

import java.time.LocalDate;

/**
 * 予約日
 */
public class ReservedDate {
    public LocalDate value;

    public ReservedDate(LocalDate value) {
        this.value = value;
    }

    public ReservedDate() {
    }

    public LocalDate value() {
        return value;
    }
}
