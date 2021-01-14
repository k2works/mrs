package mrs.infrastructure.datasource.reservation;

import mrs.application.repository.ReservableRoomRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservableRoomDataSource implements ReservableRoomRepository {
    ReservableRoomMapperExt reservableRoomMapper;

    public ReservableRoomDataSource(ReservableRoomMapperExt reservableRoomMapper) {
        this.reservableRoomMapper = reservableRoomMapper;
    }

    @Override
    public ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId) {
        return reservableRoomMapper.selectByPrimaryKey(reservableRoomId);
    }

    @Override
    public List<ReservableRoom> findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(LocalDate reservedDate) {
        return null;
    }

    @Override
    public void save(ReservableRoom reservableRoom) {

    }

    @Override
    public void delete(ReservableRoomId id) {
        reservableRoomMapper.deleteByPrimaryKey(id.reservedDate(), id.roomId());
    }
}
