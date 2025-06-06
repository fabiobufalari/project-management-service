package com.constructionhub.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource Not Found Exception
 * 
 * EN: Exception thrown when a requested resource cannot be found in the system.
 * This exception is mapped to HTTP 404 Not Found response.
 * 
 * PT: Exceção lançada quando um recurso solicitado não pode ser encontrado no sistema.
 * Esta exceção é mapeada para a resposta HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * EN: Constructs a new exception with the specified detail message.
     * PT: Constrói uma nova exceção com a mensagem de detalhe especificada.
     * 
     * @param message EN: The detail message / PT: A mensagem de detalhe
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * EN: Constructs a new exception with a formatted message for a resource identified by its ID.
     * PT: Constrói uma nova exceção com uma mensagem formatada para um recurso identificado pelo seu ID.
     * 
     * @param resourceName EN: The name of the resource type / PT: O nome do tipo de recurso
     * @param fieldName EN: The field name used for identification / PT: O nome do campo usado para identificação
     * @param fieldValue EN: The value of the field / PT: O valor do campo
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
