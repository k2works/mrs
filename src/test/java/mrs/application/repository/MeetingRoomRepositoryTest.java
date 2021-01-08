package mrs.application.repository;

import mrs.MrsApplication;
import mrs.domain.model.room.MeetingRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = MrsApplication.class)
@DisplayName("会議室レポジトリ")
public class MeetingRoomRepositoryTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Test
    @Sql("/schema.sql")
    @Sql("/data.sql")
    public void 会議室一覧を取得する() {
        MeetingRoom room = meetingRoomRepository.findById(1).get();

        assertNotNull(room);
        assertEquals(1, room.roomId().value());
        assertEquals("新木場", room.roomName().value());
    }
}
