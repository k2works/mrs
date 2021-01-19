package mrs.domain.model.room;

/**
 * 会議室
 */
public class MeetingRoom {
    private RoomId roomId;

    private RoomName roomName;

    public MeetingRoom() {
    }

    public MeetingRoom(RoomId roomId, RoomName roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public RoomId roomId() {
        return roomId;
    }

    public RoomName roomName() {
        return roomName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        MeetingRoom other = (MeetingRoom) obj;
        if (roomId == null && roomName == null) {
            return other.roomId == null && other.roomName == null;
        } else return roomId.value.equals(other.roomId.value) && roomName.value.equals(other.roomName.value);
    }
}
