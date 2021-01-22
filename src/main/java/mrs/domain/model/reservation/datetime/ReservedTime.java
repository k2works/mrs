package mrs.domain.model.reservation.datetime;

import java.time.LocalTime;

/**
 * 予約時間
 */
public class ReservedTime {
    public LocalTime start;
    public LocalTime end;

    public ReservedTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public ReservedTime() {
    }

    public LocalTime start() {
        return start;
    }

    public LocalTime end() {
        return end;
    }
}
