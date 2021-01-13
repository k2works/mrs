package mrs.infrastructure.datasource.room;

import mrs.application.repository.MeetingRoomRepository;
import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MeetingRoomDataSourceTest {
    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;

    @BeforeEach
    void clean() {
        meetingRoomMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 会議室を登録する() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(room);

        MeetingRoom result = meetingRoomRepository.findBy(1);

        assertNotNull(result);
    }

    @Test
    void 会議室を削除する() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomRepository.save(room);

        MeetingRoom result = meetingRoomRepository.findBy(1);
        meetingRoomRepository.delete(result);
        result = meetingRoomRepository.findBy(1);

        assertNull(result);
    }

    @Test
    void 会議室一覧を取得できる() {
        MeetingRoom room = new MeetingRoom(new RoomId(1), new RoomName("会議室1"));
        meetingRoomRepository.save(room);
        room = new MeetingRoom(new RoomId(2), new RoomName("会議室2"));
        meetingRoomRepository.save(room);

        List<MeetingRoom> result = meetingRoomRepository.findAll();

        assertEquals(2, result.size());
    }
}
