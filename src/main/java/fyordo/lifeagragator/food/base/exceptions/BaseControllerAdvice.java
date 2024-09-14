package fyordo.lifeagragator.food.base.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class BaseControllerAdvice {
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<?> handleNotFound() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleOtherExceptions(Throwable throwable) {
        return ResponseEntity.internalServerError().body(
                Map.of(
                        "error", throwable.getMessage(),
                        "trace", throwable.getStackTrace()
                )
        );
    }
}
