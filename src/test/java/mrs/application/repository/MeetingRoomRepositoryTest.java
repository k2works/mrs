package mrs.application.repository;

import mrs.MrsDBTest;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@MrsDBTest
@DisplayName("会議室レポジトリ")
public class MeetingRoomRepositoryTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Test
    public void 会議室一覧を取得する() {
        meetingRoomRepository.save(new MeetingRoom(new RoomId(1), new RoomName("会議室")));
        MeetingRoom room = meetingRoomRepository.findBy(1);

        assertNotNull(room);
        assertEquals(1, room.roomId().value());
        assertEquals("会議室", room.roomName().value());
    }
}
