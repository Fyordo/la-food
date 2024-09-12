package fyordo.lifeagragator.food.base.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseControllerAdvice {
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<?> handleNotFound(){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleOtherExceptions(){
        return ResponseEntity.internalServerError().build();
    }
}
