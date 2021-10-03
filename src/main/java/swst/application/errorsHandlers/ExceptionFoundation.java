package swst.application.errorsHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExceptionFoundation extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ExceptionresponsesModel.EXCEPTION_CODES exDetails;
	
	public ExceptionFoundation(ExceptionresponsesModel.EXCEPTION_CODES exDetails,String exc ) {
		super(exc);
		this.exDetails = exDetails;
	}
	
	public ExceptionresponsesModel.EXCEPTION_CODES getExceptionCode(){
		return this.exDetails;
	}


}
