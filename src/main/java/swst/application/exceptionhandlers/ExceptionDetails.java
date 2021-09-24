package swst.application.exceptionhandlers;

import java.util.Date;

import lombok.Data;

@Data
public class ExceptionDetails {
	public enum EXCEPTION_CODES {
		// Searching
		SEARCH_NOT_FOUND(1001), // Resource not found.
		SEARCH_CAN_NOT_READ(1002), // Cab't read, for some reason...

		// Saving
		SAVE_IMAGE_FAILED(2001), // Can't save image.
		SAVE_DUPLICATED(2002), // That name is already existed and can't be repeated.
		SAVE_DELETE_FAILED(2003), // Unable to delete. lol

		// Authentication
		AUTHEN_BAD_CREDENTIALS(3001), // Username or password is incorrect. Or both!
		AUTHEN_USERNAME_ALREADY_EXISTED(3002), // When registering the username but someone already took it.
		AUTHEN_REGISTERATION_FAILED(3003), // Maybe the user forgot to create their password.

		// Unvailable, at least for now.
		FEATURE_NOT_IMPLEMENTED(4001), //Not yet done.
		FEATURE_KNOWN_BUG(4002), // We just know that it is going to be an error. At least the application doesn't quit.
		FEATURE_TOO_DANGEROUS_TO_HAVE_IT(4003), // We won't let you use it...
		
		// Others
		DEAD(9999); // Just stupidly died with an unknown reason.

		private final int codeValue;

		EXCEPTION_CODES(int codeValue) {
			this.codeValue = codeValue;
		}

		public int getCodeValue() {
			return codeValue;
		}
	}

	private int exceptionCode;
	private EXCEPTION_CODES errorWith;
	private Date timeStamp;
	private String message;
	private String details;

	public ExceptionDetails(EXCEPTION_CODES errorWith, Date timeStamp, String message, String details) {
		this.errorWith = errorWith;
		this.exceptionCode = errorWith.codeValue;
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;

	}

}
