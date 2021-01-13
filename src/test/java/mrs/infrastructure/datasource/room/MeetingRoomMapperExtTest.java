package mrs.infrastructure.datasource.room;

import mrs.domain.model.room.MeetingRoom;
import mrs.domain.model.room.RoomId;
import mrs.domain.model.room.RoomName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class MeetingRoomMapperExtTest {
    @Autowired
    MeetingRoomMapperExt meetingRoomMapper;

    @BeforeEach
    void clean() {
        meetingRoomMapper.deleteByPrimaryKey(1);
    }

    @Test
    void 会議室を登録できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);

        MeetingRoom result = meetingRoomMapper.selectByPrimaryKey(1);

        assertEquals("1", result.roomId().toString());
    }

    @Test
    void 会議室を削除できる() {
        MeetingRoom record = new MeetingRoom(new RoomId(1), new RoomName("会議室"));
        meetingRoomMapper.insert(record);
        MeetingRoom result = meetingRoomMapper.selectByPrimaryKey(1);
        meetingRoomMapper.deleteByPrimaryKey(result.roomId().value());
        result = meetingRoomMapper.selectByPrimaryKey(1);

        assertNull(result);
    }
}
