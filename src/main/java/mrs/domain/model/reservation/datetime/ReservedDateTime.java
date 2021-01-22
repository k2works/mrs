package mrs.domain.model.reservation.datetime;

/**
 * 予約日時
 */
public class ReservedDateTime {
    public ReservedDate date;
    public ReservedTime time;

    public ReservedDateTime() {
    }

    public ReservedDateTime(ReservedDate reservedDate, ReservedTime reservedTime) {
        this.date = reservedDate;
        this.time = reservedTime;
    }

    public ReservedDate date() {
        return date;
    }

    public ReservedTime time() {
        return time;
    }

    @Override
    public String toString() {
        return date.toString() + ":" + time.toString();
    }
}
