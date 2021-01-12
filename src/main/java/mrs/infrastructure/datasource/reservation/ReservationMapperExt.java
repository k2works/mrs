package mrs.infrastructure.datasource.reservation;

import mrs.domain.model.reservation.ReservableRoomId;
import mrs.domain.model.reservation.Reservation;
import mrs.infrastructure.datasource.ReservationMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapperExt extends ReservationMapper {
    List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);

    List<Reservation> findAll();
}
