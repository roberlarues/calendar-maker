package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;

/**
 * Se lanza cuando ocurren errores relativos a la autorización de un usuario o aplicación
 */
public class AuthorizationException extends DefaultCalendarException {

    public AuthorizationException(String msg) {
        super(ErrorCodesConstants.ERROR_NO_AUTORIZADO, msg);
    }
}
