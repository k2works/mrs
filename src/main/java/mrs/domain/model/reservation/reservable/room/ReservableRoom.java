package mrs.domain.model.reservation.reservable.room;

import mrs.domain.model.facility.room.MeetingRoom;

/**
 * 予約可能会議室
 */
public class ReservableRoom {
    private ReservableRoomId reservableRoomId;

    private MeetingRoom meetingRoom;

    public ReservableRoom() {
    }

    public ReservableRoom(ReservableRoomId reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
    }

    public ReservableRoom(ReservableRoomId reservableRoomId, MeetingRoom meetingRoom) {
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
