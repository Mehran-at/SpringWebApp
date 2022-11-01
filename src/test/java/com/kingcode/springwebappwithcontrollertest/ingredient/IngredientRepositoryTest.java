package com.kingcode.springwebappwithcontrollertest.ingredient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @Autowired
    JdbcTemplate jdbc;

    @Test
    public void findById() {
        Optional<Ingredient> flto = ingredientRepo.findById("FLTO");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));

        Optional<Ingredient> xxxx = ingredientRepo.findById("XXXX");
        assertThat(xxxx.isEmpty()).isTrue();

    }

}