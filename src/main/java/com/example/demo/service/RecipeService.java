package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Category;
import com.example.demo.model.Ingredient;
import com.example.demo.model.IngredientLine;
import com.example.demo.model.Method;
import com.example.demo.model.Recipe;
import com.example.demo.model.User;
import com.example.demo.repository.RecipeRepo;
/**
 * Servicio que se encarga de mediar entre el controller y el repositorio de Recipe
 * @author estefgar
 *
 */
@Service
public class RecipeService {
	
	@Autowired private RecipeRepo recipeRepo;
	@Autowired private CategoryService categoryService;
	@Autowired private IngredientLineService ingredientLineService;
	@Autowired private IngredientService ingredientService;
	@Autowired private UserService userService;
	@Autowired private MethodService methodService;
	
	/**
	 *USO DE @TRANSACTIONAL
	 * La persistencia y eliminación de objetos requiere una transacción en JPA.
	 * Con JPA/Hibernate cada vez que deseemos hacer una modificación sobre la base de datos necesitamos una transacción activa.
	 * Para asegurarnos de que se esté ejecutando una transacción, lo que hacemos es anotar el método con @Transactional.
	 */
	
	/**
	 * MÉTODO para añadir una nueva receta a la base de datos
	 * @param recipe
	 * @return receta que se ha añadido
	 */
	@Transactional
	public Recipe addRecipeBBDD(Recipe recipe) {
		return this.recipeRepo.save(recipe);
	}
	
	/**
	 * MÉTODO para borrar una receta de la base de datos
	 * @param recipe
	 */
	@Transactional
	public void deleteRecipe(Recipe recipe) {
		recipe.setUser(null);
		this.recipeRepo.delete(recipe);
	}
	/**
	 * MÉTODO para encontrar la lista de recetas que pertenece a un usuario
	 * @param id
	 * @return lista de recetas del usuario
	 */
	@Transactional
	public List<Recipe> findRecipeListUser(Long id){
		return this.recipeRepo.findByUserId(id);
	}
	
	/**
	 * MÉTODO para buscar una receta a través de su id
	 * @param id
	 * @return receta que coincide con ese id
	 */
	@Transactional
	public Recipe findRecipeById(Integer id) {
		return this.recipeRepo.findById(id).orElse(null);
	}
	
	/**
	 * MÉTODO para devolver todas las recetas del repositorio
	 * @return listado de todas las recetas existentes
	 */
	public List<Recipe> findAllRecipes(){
		return this.recipeRepo.findAll();
	}
	
	/**
	 * MÉTODO para devolver todas las recetas del repositorio que ya han sido aprobadas por el administrador
	 * @return listado de todas las recetas aprobadas por el administrador
	 */
	@Transactional
	public List<Recipe> findAllRecipesNotPending(){
		return this.recipeRepo.findRecipesNotPending();
	}
	
	/**
	 * MÉTODO para devolver todas las recetas del repositorio que no han sido aprobadas por el administrador
	 * @return listado de todas las recetas que no han sido aprobadas por el administrador
	 */
	@Transactional
	public List<Recipe> findAllRecipesPending(){
		return this.recipeRepo.findRecipesPending();
	}
	
	/**
	 * MÉTODO que devuelve todas las recetas de una categoría en concreto
	 * @param id
	 * @return lista de recetas de la categoría que coincide con el id que se le pasa por parámetro
	 */
	@Transactional
	public List<Recipe> findAllRecipesByCategory(Integer id){
		return this.recipeRepo.findRecipesByCategory(id);
	}
	
	/**
	 * MÉTODO para obtener las recetas que coinciden con el nombre que se le pasa por parámetro
	 * @param recipeName
	 * @return lista de recetas que coinciden cone se nombre
	 */
	@Transactional
	public List<Recipe> findRecipesByName(String recipeName){
		return this.recipeRepo.findRecipeByName(recipeName);
	}
	
	/**
	 * MÉTODO para obtener las recetas que contienen en su nombre parte del nombre que se le pasa por parámetro
	 * @param recipeName
	 * @return lista de recetas que contienen parte del nombre que se le pasa
	 */
	@Transactional
	public List<Recipe> findRecipesBySimilarName(String recipeName){
		return this.recipeRepo.findRecipeBySimilarName(recipeName);
	}
	
	/**
	 * Método para editar los campos de una receta
	 * @param editRecipe
	 * @param idRecipe
	 * @return
	 */
	@Transactional
	public Recipe editRecipe(Recipe editRecipe, Integer idRecipe) {
		
		//receta que se quiere editar
		Recipe recipeREPO = this.findRecipeById(idRecipe);
		
		//categoría nueva de la receta
		Integer idCategory = editRecipe.getCategory().getId();
		Category cat = this.categoryService.findById(idCategory);


		recipeREPO.setCategory(cat);
		//añado los nuevos ingredientes a la bbdd
		this.ingredientService.addIngredient(editRecipe);
		
		List<IngredientLine> IngLineList = this.ingredientLineService.addIngredientLine(editRecipe);
		List<Method> MethodRecipe = this.methodService.addMethod(editRecipe);
		
		this.ingredientLineService.deleteLine(recipeREPO.getId());
		recipeREPO.setIngredientLine(IngLineList);
		recipeREPO.setMethod(MethodRecipe);
		recipeREPO.setCategory(cat);
		
		for (IngredientLine line : IngLineList) {
			line.setRecipe(recipeREPO);
			
		}
		recipeREPO.setIngredientLine(IngLineList);
		recipeREPO.setMethod(MethodRecipe);
		recipeREPO.setRecipeName(editRecipe.getRecipeName());
		if(editRecipe.getFile()!=null) {
		recipeREPO.setFileID(editRecipe.getFile());
	}
		recipeREPO.setComments(null);
		recipeREPO.setIsPending(true);
		recipeREPO.setFecha(editRecipe.getFecha());
		
		this.methodService.deleteMethodwihtoutRecipe();
		
		return this.recipeRepo.save(recipeREPO);
		

		
	}
	
	
	/**
	 * MÉTODO para comprobar si una receta ya contiene a un ingrediente concreto a través de una consulta
	 * @param ingredientName
	 * @return 0 si no lo contiene, !=0 si contiene el ingrediente
	 */
	public Integer checkRecipeIngredient(String ingredientName) {
		return this.recipeRepo.isIngredientExists(ingredientName);
	}
	
	/**
	 * MÉTODO para comprobar si ya existe una receta con ese nombre a través de una consulta
	 * @param recipeName
	 * @return 0 si no existe, !=0 si ya existe una receta con ese nombre
	 */
	public Integer checkRecipeName(String recipeName) {
		return this.recipeRepo.isRecipeNameExists(recipeName);
	}
	
	/**
	 * MÉTODO para buscar al usuario de una receta a través de una consulta
	 * @param recipeID
	 * @return el usuario que coincide con el id de una receta
	 */
	public User getUserByRecipeID(Integer recipeID) {
		long idUser =  this.recipeRepo.getUserIDFromRecipe(recipeID);
		return this.userService.findById(idUser);
	}
	

	/**
	 * MÉTODO que elimina todas las recetas de un usuario
	 * @param user
	 */
	public void deleteAllRecipesUser(User user) {
		List<Recipe> listRecipesUser = this.findRecipeListUser(user.getId());
		int index = listRecipesUser.size();
		while (index>0) {
			this.deleteRecipe(listRecipesUser.get(index-1));
			index--;

		}
	}
	
	/**
	 * MÉTODO que busca las recetas que contienen una serie de ingredientes
	 * @param ingredientList
	 * @param number
	 * @return lista de recetas que contienen los ingredientes que se pasan en la lista
	 */
	@Transactional
	public List<Recipe> findRecipesFromIngredients(List<String> ingredientList, int number){
		return this.recipeRepo.findRecipesFromIngredients(ingredientList, ingredientList.size());
	}
	
	


}

