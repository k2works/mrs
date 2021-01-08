package mrs.domain.model.reservation;

import java.time.LocalTime;

/**
 * 予約時間
 */
public class ReservedTime {
    LocalTime start;
    LocalTime end;

    public ReservedTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime start() {
        return start;
    }

    public LocalTime end() {
        return end;
    }
}
