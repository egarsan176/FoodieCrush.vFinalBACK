package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Ingredient;
import com.example.demo.model.IngredientLine;
import com.example.demo.model.Recipe;
import com.example.demo.repository.IngredientLineRepo;

/**
 * Servicio que se encarga de mediar entre el controller y el repositorio de IngredientLine
 * @author estefgar
 *
 */
@Service
public class IngredientLineService {
	
	@Autowired
	private IngredientLineRepo ingredientLineREPO;
	@Autowired 
	private IngredientService ingredientService;
	
	/**
	 * MÉTODO para almacenar una línea de ingredientes en la bbdd
	 * @param ingredientLine
	 * @return línea de ingredientes que se ha almacenado
	 */
	@Transactional
	public IngredientLine add(IngredientLine ingredientLine) {
		return this.ingredientLineREPO.save(ingredientLine);
	}
	
	/**
	 * MÉTODO para encontrar todas las líneas de ingredientes almacenadas en la bbdd
	 * @return lista con todas las líneas de la bbdd
	 */
	public List<IngredientLine> findAll(){
		return this.ingredientLineREPO.findAll();
	}
	
	/**
	 * MÉTODO para añadir todas las líneas de ingredientes de una nueva receta
	 * @param recipe
	 * @return listado líneas de ingredientes de la receta
	 */
	@Transactional
	public List<IngredientLine> addIngredientLine(Recipe recipe) {
		

		
		List<IngredientLine> line = recipe.getIngredientLine();
		List<IngredientLine> result = new ArrayList<>();
		
		for (IngredientLine linea : line) {
			Ingredient ingRecipe = linea.getIngredient();
			Ingredient ingREPO = this.ingredientService.getIngredientByName(ingRecipe.getName());
			linea.setIngredient(ingREPO);
			linea.setAmount(linea.getAmount());
			result.add(this.ingredientLineREPO.save(linea));
		}
		
		return result;
	}
	
	/**
	 * MÉTODO para encontrar una línea de ingredientes a través de su id
	 * @param id
	 * @return
	 */
	public IngredientLine findById(Integer id) {
		return this.ingredientLineREPO.findById(id).orElse(null);
	}
	
	/**
	 * MÉTODO para editar la cantidad de una línea de ingredientes
	 * @param ingredientLine
	 * @param amount 
	 * @return la línea de ingredientes con su cantidad editada
	 */
	public IngredientLine edit(IngredientLine ingredientLine, Integer amount) {
		ingredientLine.setAmount(amount);
		return this.ingredientLineREPO.save(ingredientLine);
	}
	
	
	/**
	 * MÉTODO que busca una línea de ingredientes a través de un ingrediente que se le pasa por parámetro
	 * @param ingredient
	 * @return línea de ingrediente que coincide con el ingrediente que se le pasa
	 */
	public IngredientLine findByIngredient(Ingredient ingredient) {
		return this.ingredientLineREPO.findByIngredient(ingredient);
	}
	
	public void deleteLine(int recipeID) {
		this.ingredientLineREPO.deleteLines(recipeID);
	}
	
	
	
	
	
	
	
	

}
