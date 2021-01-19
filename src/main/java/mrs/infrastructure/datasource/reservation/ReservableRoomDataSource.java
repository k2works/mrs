package mrs.infrastructure.datasource.reservation;

import mrs.application.repository.ReservableRoomRepository;
import mrs.domain.model.reservation.ReservableRoom;
import mrs.domain.model.reservation.ReservableRoomId;
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
        return reservableRoomMapper.findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(reservedDate);
    }

    @Override
    public void save(ReservableRoom reservableRoom) {
        reservableRoomMapper.insert(reservableRoom);
    }

    @Override
    public void delete(ReservableRoomId id) {
        reservableRoomMapper.deleteByPrimaryKey(id.reservedDate().value(), id.roomId().value());
    }

    @Override
    public void deleteAll() {
        reservableRoomMapper.selectAll().forEach(i -> reservableRoomMapper.deleteByPrimaryKey(i.reservableRoomId().reservedDate().value(), i.reservableRoomId().roomId().value()));
    }

    @Override
    public List<ReservableRoom> findAll() {
        return reservableRoomMapper.selectAll();
    }
}
