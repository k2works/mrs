package mrs.domain.model.facility.room;

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

    public RoomId() {
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        RoomId other = (RoomId) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }
}
