package mrs.domain.model.reservation;

import java.time.LocalDate;

/**
 * 予約日
 */
public class ReservedDate {
    LocalDate value;

    public ReservedDate(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return value;
    }
}
