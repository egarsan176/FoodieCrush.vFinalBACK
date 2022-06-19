package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Method;
import com.example.demo.model.Recipe;
import com.example.demo.repository.MethodRepo;
/**
 * Servicio que se encarga de mediar entre el controller y el repositorio de Method
 * @author estefgar
 *
 */
@Service
public class MethodService {

	@Autowired private MethodRepo methodREPO;
	
	/**
	 * A este método se accede para eliminar una lista de métodos de la base de datos
	 * @param recipeMethod
	 */
	public void deleteMethod(List<Method> recipeMethod) {
		for (Method method : recipeMethod) {
			this.methodREPO.delete(method);
		}
		
	}
	
	/**
	 * MÉTODO para añadir un nuevo método a la receta
	 * @param recipe
	 * @return
	 */
	@Transactional
	public List<Method> addMethod(Recipe recipe) {
		List<Method> listMethodREPO = new  ArrayList<>();
		for (Method step : recipe.getMethod()) {
			listMethodREPO.add(this.methodREPO.save(step));
		}
		return listMethodREPO;
	}
	
	/**
	 * MÉTODO para borrar los métodos que se quedan colgados sin receta
	 */
	@Transactional
	public void deleteMethodwihtoutRecipe() {
		this.methodREPO.deleteMethodWithoutRecipe();
	}
}
