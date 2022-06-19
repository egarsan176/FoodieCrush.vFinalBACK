package com.example.demo.repository;
/**
 * Encargada de la persistencia de datos de Method
 * @author estefgar
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Method;

public interface MethodRepo extends JpaRepository<Method, Integer> {
	
	/**
	 * CONSULTA para eliminar los métodos que se quedan sin receta
	 */
	@Query(value="delete from method where id not in (select method_id from recipe_method)", nativeQuery = true)
	public void deleteMethodWithoutRecipe();

}
