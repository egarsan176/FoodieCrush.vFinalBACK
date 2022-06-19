package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Ingredient;
import com.example.demo.model.IngredientLine;
import com.example.demo.model.Recipe;
import com.example.demo.repository.IngredientRepo;

@Service
public class IngredientService {
	
	@Autowired private IngredientRepo ingredientREPO;
	/**
	 * MÉTODO para añadir un ingrediente a la bbdd
	 * @param ingredient
	 */
	@Transactional
	public void addIngredient(Recipe recipe) {
		List<IngredientLine> list = recipe.getIngredientLine();
		for (IngredientLine line : list) {
			Ingredient ingred = line.getIngredient();
			ingred.setName(ingred.getName().toUpperCase());
			
			Integer idIng = this.ingredientREPO.getIdFromIngredient(ingred.getName());
			
			if(idIng == null) {
				this.ingredientREPO.save(ingred);
			}else {
				this.ingredientREPO.findById(idIng).orElse(null);
			}
		}

		
	}
	@Transactional
	public void checkStatusIngredient(Ingredient ingredient) {
		ingredient.setPending(false);
		this.ingredientREPO.save(ingredient);
	}
	@Transactional
	public List<Ingredient> findAllIngredients(){
		return this.ingredientREPO.findAll();
	}
	
	public Ingredient getIngredientByName(String name) {
		return this.ingredientREPO.findByName(name);
	}
	
	/**
	 * MÉTODO para conseguir el listado de ingredientes de la base de datos no repetidos
	 * @return lista de ingredientes almacenados en la base de datos
	 */
	public List<Ingredient> getAllIngredientsFromBDWithoutRep(){
	    return this.getNoRepited(this.ingredientREPO.findAll());

	}
	
	public List<Ingredient> getNoRepited(List<Ingredient> listaING) {
	    List<String> listaNombresIng = new ArrayList<>();
	    
	    
	    for (Ingredient ingredient : listaING) {
	    	listaNombresIng.add(ingredient.getName());
		}
	    
	    List<String> listaNombresSINrepe = new ArrayList<>();
	    List<Integer> listaIndex = new ArrayList<>(); 
	    
	    
	    for (int j = 0; j < listaNombresIng.size(); j++) {
			String nombre = listaNombresIng.get(j).toUpperCase();
			if(!listaNombresSINrepe.contains(nombre)) {
				listaNombresSINrepe.add(nombre.toUpperCase());
				listaIndex.add(j);
			}
	    }
	    
	    List<Integer> listaIndex2 = new ArrayList<>();
	    
	    //para conseguir los índices de los elementos repetidos
	    for (int i = 0; i < listaING.size(); i++) {
	    	if(!listaIndex.contains(i)) {
	    		listaIndex2.add(i);
	    	}
		}
	    
	    Collections.reverse(listaIndex2);
	    //elimino de la lista de ingredientes los ingredientes con nombres repetidos
	    for (int i = 0; i < listaIndex2.size(); i++) {
	    	int index= listaIndex2.get(i);
	    	listaING.remove(index);
		}
	    
	    List<Ingredient> resultado = listaING;
	    //System.out.println(resultado);
	    
		return resultado;
	}
	
	/**
	 * MÉTODO para conseguir los nombres de los ingredientes de la base de datos sin repetirse
	 * @return una lista con los nombres de los ingredientes de la base de datos
	 */
	public List<String> getNameAllIngredientsFromBD(){
		
		List<Ingredient> list = this.getAllIngredientsFromBDWithoutRep();
		List<String> listaNombresIng = new ArrayList<>();
		
		  for (Ingredient ingredient : list) {
		    	listaNombresIng.add(ingredient.getName());
			}
		
		
		return listaNombresIng;
	}
	
	public List<String> getAllNames(){
		return this.ingredientREPO.getNameAllIngredients();
	}
	

	
	/**
	 * MÉTODO para obtener los ingredientes con estado is_pending=false
	 * @return lista con los ingredientes aprobados de la base de datos
	 */
	public List<Ingredient> getIngredientsApproved(){
		return this.getNoRepited((this.ingredientREPO.getIngredientsApproved()));
	}
	
	/**
	 * MÉTODO para obtener los ingredientes con estado is_pending=true
	 * @return lista con los ingredientes no aprobados de la base de datos
	 */
	public List<Ingredient> getIngredientsPending(){
		return this.getNoRepited((this.ingredientREPO.getIngredientsPending()));
	}
	
	public Ingredient getIngredientByID(Integer id) {
		return this.ingredientREPO.findById(id).orElse(null);
	}


}
