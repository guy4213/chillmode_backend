package guy.example.FinalprojectUpdated.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE )
public class RateOutOFRangeException extends RuntimeException{

    private final float rate;
    private final String message;

    public RateOutOFRangeException(float rate) {
        this(rate, "rate cannot exceed 0-10");
    }
    public RateOutOFRangeException(float rate, String message) {
        super("%.2f %s".formatted(rate,  message));
    this.rate=rate;
    this.message=message;
    }


}