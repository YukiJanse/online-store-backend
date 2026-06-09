//package se.jensen.yuki.productservice.shared.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors()
//                .forEach(fieldError ->
//                        errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
//
//        return ResponseEntity.badRequest().body(errors);
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.notFound().header("UserNotFound", ex.getMessage()).build();
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
//        log.warn(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//}
