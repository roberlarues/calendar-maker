package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;
import lombok.Getter;
import lombok.ToString;

/**
 * Excepciones propias de la aplicación
 */
@Getter
@ToString
public abstract class DefaultCalendarException extends RuntimeException {

	private String code;
	private String errorMsg;

	/**
	 * Sólo para propagar excepciones. Para crear nuevas deben usarse las
	 * excepciones específicas (ComunicationException, ValidationException, etc.)
	 * 
	 * @param errorMsg
	 * @param t
	 */
	public DefaultCalendarException(String errorMsg, Throwable t) {
		super(t);
		this.code = ErrorCodesConstants.ERROR_DEFAULT;
		this.errorMsg = errorMsg;
	}

	protected DefaultCalendarException(String code, String errorMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
	}

	protected DefaultCalendarException(String code, String errorMsg, Throwable t) {
		super(t);
		this.code = code;
		this.errorMsg = errorMsg;
	}
}
