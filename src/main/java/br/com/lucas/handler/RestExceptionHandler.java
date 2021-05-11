package br.com.lucas.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        return new ResponseEntity<ApiError>(new ApiError(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    class ApiError {
        private int status;
        private Map<String, List<String>> customErrors = new HashMap<>();

        public ApiError(int status, List<FieldError> fieldErrors) {
            this.status = status;
            setErrors(fieldErrors);
        }

        public void setErrors(List<FieldError> errors) {
            errors.forEach(error -> {
                List<String> errorsMessage = customErrors.get(error.getField());
                if(Objects.nonNull(errorsMessage)){
                    errorsMessage.add(error.getDefaultMessage());
                }else{
                    List<String> erros = new ArrayList<>();
                    erros.add(error.getDefaultMessage());
                    customErrors.put(error.getField(), erros);
                }
            });
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Map<String, List<String>> getCustomErrors() {
            return customErrors;
        }

        public void setCustomErrors(Map<String, List<String>> customErrors) {
            this.customErrors = customErrors;
        }
    }
}
