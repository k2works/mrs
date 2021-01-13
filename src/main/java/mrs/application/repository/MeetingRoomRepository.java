package mrs.application.repository;

import mrs.domain.model.room.MeetingRoom;

import java.util.List;

/**
 * 会議室リポジトリ
 */
public interface MeetingRoomRepository {
    void save(MeetingRoom room);
    void delete(MeetingRoom room);
    MeetingRoom findBy(Integer roomId);
    List<MeetingRoom> findAll();
}
