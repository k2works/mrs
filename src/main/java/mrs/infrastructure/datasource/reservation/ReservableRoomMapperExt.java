package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.reservable.room.ReservableRoom;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.infrastructure.datasource.ReservableRoomMapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservableRoomMapperExt extends ReservableRoomMapper {
    ReservableRoom selectByPrimaryKey(ReservableRoomId reservableRoomId);

    List<ReservableRoom> selectAll();

    List<ReservableRoom> findByReservableRoomId_ReservedDateOrderByReservableRoomId_RoomIdAsc(LocalDate reservedDate);
}
