package mrs.app.reservation;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@EndTimeMustBeAfterStartTime(message = "開始時刻は終了時刻より後にしてください")
public class ReservationForm implements Serializable {
    @NotNull(message ="必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
