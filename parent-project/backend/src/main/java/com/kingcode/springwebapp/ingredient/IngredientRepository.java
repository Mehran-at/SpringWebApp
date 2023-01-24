package com.kingcode.springwebapp.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="http://tacocloud:8080")
public interface IngredientRepository
    extends CrudRepository<Ingredient, String> {

}

