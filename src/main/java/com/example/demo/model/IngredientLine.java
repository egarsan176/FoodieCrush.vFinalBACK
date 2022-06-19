package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class IngredientLine {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer line_id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id")	
	private Ingredient ingredient;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")  
	@JsonIgnore
	private Recipe recipe;
	
	private int amount;
	
	
	public IngredientLine(Ingredient ingredient, int amount) {
		super();
		this.ingredient = ingredient;
		this.amount = amount;
	}


	public IngredientLine() {
		super();
	}


	public Integer getId() {
		return line_id;
	}


	public void setId(Integer id) {
		this.line_id = id;
	}


	public Ingredient getIngredient() {
		return ingredient;
	}


	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "IngredientLine [id=" + line_id + ", ingredient=" + ingredient + ", amount=" + amount + "]";
	}




	public Recipe getRecipe() {
		return recipe;
	}


	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
	
	

}
