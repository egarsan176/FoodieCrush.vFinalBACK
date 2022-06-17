package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Excepción para cuando no se encuentra una receta por su id
 * @author estefgar
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public RecipeNotFoundException(Integer id) {
		super("No existe ninguna receta con id "+id+".");
		
	}

	

}
