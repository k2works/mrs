package mrs.domain.model.reservation;

import mrs.domain.model.room.MeetingRoom;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 予約可能会議室
 */
@Entity
public class ReservableRoom implements Serializable {
    @EmbeddedId
    private ReservableRoomId reservableRoomId;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    @MapsId("roomId")
    private MeetingRoom meetingRoom;

    public ReservableRoom() {
    }

    public ReservableRoom(ReservableRoomId reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
    }

    public ReservableRoom(ReservableRoomId reservableRoomId, MeetingRoom meetingRoom) {
        // TODO 値オブジェクトに置き換える
        this.reservableRoomId = reservableRoomId;
        this.meetingRoom = meetingRoom;
    }

    public MeetingRoom meetingRoom() {
        return meetingRoom;
    }

    public ReservableRoomId reservableRoomId() {
        return reservableRoomId;
    }

}
