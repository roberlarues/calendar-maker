package es.rcsdevs.calendarmaker.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Contenedor de datos de error
 */
@Data
@AllArgsConstructor
public class ErrorDTO {
    private String code;
    private String message;
}
