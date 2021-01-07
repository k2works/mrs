package mrs.domain.model.room;

/**
 * 会議室ID
 */
public class RoomId {
    Integer value;

    public RoomId(Integer value) {
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
