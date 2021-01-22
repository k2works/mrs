package mrs.infrastructure.datasource.room;

import mrs.application.repository.MeetingRoomRepository;
import mrs.domain.model.facility.room.MeetingRoom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetingRoomDataSource implements MeetingRoomRepository {
    MeetingRoomMapperExt meetingRoomMapper;

    public MeetingRoomDataSource(MeetingRoomMapperExt meetingRoomMapper) {
        this.meetingRoomMapper = meetingRoomMapper;
    }

    @Override
    public void save(MeetingRoom room) {
        meetingRoomMapper.insert(room);
    }

    @Override
    public void delete(MeetingRoom room) {
        meetingRoomMapper.deleteByPrimaryKey(room.roomId());
    }

    @Override
    public MeetingRoom findBy(Integer roomId) {
        return meetingRoomMapper.selectByPrimaryKey(roomId);
    }

    @Override
    public List<MeetingRoom> findAll() {
        return meetingRoomMapper.selectAll();
    }

    @Override
    public void deleteAll() {
        meetingRoomMapper.selectAll().forEach(i -> meetingRoomMapper.deleteByPrimaryKey(i.roomId()));
    }

}
