package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.Reservation;
import mrs.domain.model.reservation.reservable.room.ReservableRoomId;
import mrs.infrastructure.datasource.ReservationMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapperExt extends ReservationMapper {
    List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);

    List<Reservation> findAll();

    Reservation findById(int reservationId);

    void deleteAll();
}
