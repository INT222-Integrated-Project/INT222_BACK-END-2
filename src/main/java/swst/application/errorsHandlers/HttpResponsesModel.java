package swst.application.errorsHandlers;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class HttpResponsesModel {
	private int responseCode;
	private HttpStatus httpStatus;
	private String reason;
	private String mssg;

}
