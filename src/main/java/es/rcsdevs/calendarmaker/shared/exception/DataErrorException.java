package es.rcsdevs.calendarmaker.shared.exception;

import es.rcsdevs.calendarmaker.shared.constants.ErrorCodesConstants;

/**
 * Excepción para controlar cuando un dato existente en el sistema o
 * proporcionado por otros servicios no es correcto
 */
public class DataErrorException extends DefaultCalendarException {

    public DataErrorException(String msg) {
        super(ErrorCodesConstants.ERROR_DATO_ERRONEO, msg);
    }

    public DataErrorException(String msg, Throwable t) {
        super(ErrorCodesConstants.ERROR_DATO_ERRONEO, msg, t);
    }
}
