package com.bufalari.building.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when an operation is not allowed due to business rules
 * or the current state of a resource.
 * Maps to HTTP 409 Conflict by default, as it often indicates a conflict with
 * the current state of the resource that prevents the operation.
 * Could also be mapped to 400 Bad Request in some contexts.
 *
 * Exceção customizada lançada quando uma operação não é permitida devido a regras de negócio
 * ou ao estado atual de um recurso.
 * Mapeia para HTTP 409 Conflict por padrão, pois frequentemente indica um conflito com
 * o estado atual do recurso que impede a operação.
 * Poderia também ser mapeada para 400 Bad Request em alguns contextos.
 */
@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict é uma boa escolha para "operação não permitida devido ao estado"
public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException(String message) {
        super(message);
    }

    public OperationNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}