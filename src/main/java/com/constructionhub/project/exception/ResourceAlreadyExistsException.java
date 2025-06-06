package com.constructionhub.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource Already Exists Exception
 * 
 * EN: Exception thrown when attempting to create a resource that already exists in the system.
 * This exception is mapped to HTTP 409 Conflict response.
 * 
 * PT: Exceção lançada ao tentar criar um recurso que já existe no sistema.
 * Esta exceção é mapeada para a resposta HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * EN: Constructs a new exception with the specified detail message.
     * PT: Constrói uma nova exceção com a mensagem de detalhe especificada.
     * 
     * @param message EN: The detail message / PT: A mensagem de detalhe
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * EN: Constructs a new exception with a formatted message for a resource identified by a field.
     * PT: Constrói uma nova exceção com uma mensagem formatada para um recurso identificado por um campo.
     * 
     * @param resourceName EN: The name of the resource type / PT: O nome do tipo de recurso
     * @param fieldName EN: The field name that causes the conflict / PT: O nome do campo que causa o conflito
     * @param fieldValue EN: The value of the field / PT: O valor do campo
     */
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
