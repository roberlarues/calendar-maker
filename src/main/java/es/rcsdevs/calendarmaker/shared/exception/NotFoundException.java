package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;

/**
 * Excepción para controlar cuando un dato solicitado no existe
 */
public class NotFoundException extends DefaultCalendarException {

    public NotFoundException(String msg) {
        super(ErrorCodesConstants.ERROR_NO_ENCONTRADO, msg);
    }
}
