package mrs.application.repository;

import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;

/**
 * 予約可能会議室レポジトリ
 */
public interface ReservableRoomRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId);

    List<ReservableRoom> findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(LocalDate reservedDate);

    void save(ReservableRoom reservableRoom);

    void delete(ReservableRoomId id);

    void deleteAll();

    List<ReservableRoom> findAll();
}
