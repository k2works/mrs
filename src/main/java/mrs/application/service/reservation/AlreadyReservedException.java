package mrs.application.service.reservation;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException(String message) {
        super(message);
    }
}
