package mrs.domain.model.reservation;

/**
 * 予約ID
 */
public class ReservationId {
    Integer value;

    public ReservationId(int value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
