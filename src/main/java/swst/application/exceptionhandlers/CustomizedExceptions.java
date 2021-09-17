package swst.application.exceptionhandlers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedExceptions {

	@ExceptionHandler(ExceptionFoundation.class)
	public ResponseEntity<Object> handlException(ExceptionFoundation exc, WebRequest request) {
		ExceptionDetails response = new ExceptionDetails(exc.getExceptionCode(), new Date(), exc.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.I_AM_A_TEAPOT);
	}

}
