package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;

/**
 * Excepción para indicar que una acción es denegada porque no es válida
 */
public class ValidationException extends DefaultCalendarException {

    public ValidationException(String msg) {
        super(ErrorCodesConstants.ERROR_VALIDACION, msg);
    }
}

