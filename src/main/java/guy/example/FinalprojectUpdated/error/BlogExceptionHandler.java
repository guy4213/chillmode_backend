package guy.example.FinalprojectUpdated.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BlogExceptionHandler {
    //prefer DTOS!
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<Map<String, Object>>
    handle(
            BlogException e,
            HandlerMethod method,
            HttpServletRequest request

    ) {
        var map = new HashMap<>(getExcMap(e, method, request));

        //if e doesn't have the Response status annotation: return 500
        if (!e.getClass().isAnnotationPresent(ResponseStatus.class)) {
            return ResponseEntity.internalServerError().body(map);
        }

        //else take the code from the annotation:
        var annotation = e.getClass().getAnnotation(ResponseStatus.class);

        var code = annotation.code();

        return new ResponseEntity<>(map, code);
    }



    //specific exceptions SQLIntegrityConstraintViolationException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>>
    handle(
            SQLIntegrityConstraintViolationException exc,
            HandlerMethod method,
            HttpServletRequest request
    ) {

        var controller = method.getMethod().getDeclaringClass().getSimpleName();
        return ResponseEntity.badRequest().body(
                Map.of(
                        "controller", controller,
                        "controllerMethod", method.getMethod().getName(),
                        "method", request.getMethod(),
                        "path", request.getRequestURI(),
                        "status", HttpStatus.BAD_REQUEST.toString(),
                        "message", exc.getMessage(),
                        "timestamp", LocalDateTime.now().toString()
                )
        );
    }
    //specific exceptions MethodArgumentNotValidException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
    handle(
            MethodArgumentNotValidException exc,
            HandlerMethod method,
            HttpServletRequest request
    ) {
        var map = new HashMap<>(getExcMap(exc, method, request));
        exc.getBindingResult().getFieldErrors().forEach(
                e -> map.put("Field_" + e.getField(), e.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(map);
    }



    
//shortcut-Helper Method
    private Map<String, Object>
    getExcMap(
            Exception exc,
            HandlerMethod method,
            HttpServletRequest request
    ) {
        var controller = method.getMethod().getDeclaringClass().getSimpleName();
        var methodName = method.getMethod().getName();
        var httpMethod = request.getMethod();
        var path = request.getRequestURI();
        return Map.of(
                "method", httpMethod,
                "path", path,
                "message", exc.getMessage(),
                "controller", controller,
                "controllerMethod", methodName,
                "status", HttpStatus.BAD_REQUEST.value()
        );
    }



    //CATCH ALL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handle(
            Exception e,
            HandlerMethod method,
            HttpServletRequest request
    ) {
        var map=new HashMap<>(getExcMap(e,method,request));
        map.put("internalServerError","Contact Admin");
        return  ResponseEntity.badRequest().body(map);
    }






}