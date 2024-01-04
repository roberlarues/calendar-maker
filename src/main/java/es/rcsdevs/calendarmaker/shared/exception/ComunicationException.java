package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;

/**
 * Excepciones de fallo en las comunicaciones
 */
public class ComunicationException extends DefaultCalendarException {

    public ComunicationException(String msg) {
        super(ErrorCodesConstants.ERROR_COMUNICACION, msg);
    }
}
