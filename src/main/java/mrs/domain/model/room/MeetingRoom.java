package mrs.domain.model.room;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 会議室
 */
@Entity
public class MeetingRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    private String roomName;

    public MeetingRoom() {
    }

    public MeetingRoom(RoomId roomId, RoomName roomName) {
        this.roomId = roomId.value;
        this.roomName = roomName.value;
    }

    public RoomId roomId() {
        return new RoomId(roomId);
    }

    public RoomName roomName() {
        return new RoomName(roomName);
    }
}
