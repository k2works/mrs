package mrs.infrastructure.datasource.room;

import mrs.domain.model.room.MeetingRoom;
import mrs.infrastructure.datasource.MeetingRoomMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingRoomMapperExt extends MeetingRoomMapper {
    List<MeetingRoom> selectAll();
}
