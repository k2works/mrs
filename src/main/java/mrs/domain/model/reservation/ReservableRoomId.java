package mrs.domain.model.reservation;

import mrs.domain.model.room.RoomId;

import java.io.Serializable;

/**
 * 予約可能会議室ID
 */
public class ReservableRoomId implements Serializable {

    private RoomId roomId;

    private ReservedDate reservedDate;

    public ReservableRoomId() {
    }

    public ReservableRoomId(RoomId roomId, ReservedDate reservedDate) {
        this.roomId = roomId;
        this.reservedDate = reservedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reservedDate == null) ? 0 : reservedDate.hashCode());
        result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        ReservableRoomId other = (ReservableRoomId) obj;
        if (reservedDate == null) {
            if (other.reservedDate != null) return false;
        } else if (!reservedDate.value.equals(other.reservedDate.value)) {
            return false;
        }
        if (roomId == null) {
            if (other.roomId != null) return false;
        } else if (!roomId.value().equals(other.roomId.value()))
            return false;
        return true;
    }

    public RoomId roomId() {
        return roomId;
    }

    public ReservedDate reservedDate() {
        return reservedDate;
    }
}
