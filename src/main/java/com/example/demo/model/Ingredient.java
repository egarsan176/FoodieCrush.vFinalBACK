package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ingredient {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Integer id;
	private String name;
	private boolean isPending;
	
	
	@OneToMany(mappedBy="ingredient", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<IngredientLine> ingredientLine = new ArrayList<>();
	
	public Ingredient(String name) {
		super();
		this.name = name;
	}

	public Ingredient() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + "]";
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public List<IngredientLine> getIngredientLine() {
		return ingredientLine;
	}

	public void setIngredientLine(List<IngredientLine> ingredientLine) {
		this.ingredientLine = ingredientLine;
	}


	
	
	
	

}
