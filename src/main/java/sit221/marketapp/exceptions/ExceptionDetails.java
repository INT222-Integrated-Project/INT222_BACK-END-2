package sit221.marketapp.exceptions;

import java.util.Date;

public class ExceptionDetails {

	private Date timeStamp;
	private String message;
	private String details;

	public ExceptionDetails(Date timeStamp, String msg, String details) {
		this.timeStamp = timeStamp;
		this.message = msg;
		this.details = details;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorValue() {
		return details;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrorValue(String details) {
		this.details = details;
	}

}
