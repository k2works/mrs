package mrs.application.repository;

import mrs.domain.model.room.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 会議室リポジトリ
 */
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {
}
