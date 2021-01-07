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

    public MeetingRoom(Integer roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Integer roomId() {
        return roomId;
    }

    public String roomName() {
        return roomName;
    }
}
