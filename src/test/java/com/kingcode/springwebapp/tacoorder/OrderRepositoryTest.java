package com.kingcode.springwebapp.tacoorder;

import com.kingcode.springwebapp.ingredient.Ingredient;
import com.kingcode.springwebapp.ingredient.Ingredient.Type;
import com.kingcode.springwebapp.taco.Taco;
import com.kingcode.springwebapp.taco.TacoUDRUtils;
import com.kingcode.springwebapp.taco.TacoUDT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepo;

    @Test
    public void saveOrderWithTwoTacos() {
        Order order = new Order();
        order.setDeliveryName("Test McTest");
        order.setDeliveryStreet("1234 Test Lane");
        order.setDeliveryCity("Testville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("80123");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/23");
        order.setCcCVV("123");
        Taco taco1 = new Taco();
        taco1.setName("Taco One");
        taco1.addIngredient(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        taco1.addIngredient(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        taco1.addIngredient(new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE));
        order.addTaco(TacoUDRUtils.toTacoUDT(taco1));
        Taco taco2 = new Taco();
        taco2.setName("Taco Two");
        taco2.addIngredient(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        taco2.addIngredient(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        taco2.addIngredient(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        order.addTaco(TacoUDRUtils.toTacoUDT(taco2));

        Order savedOrder = orderRepo.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        Order fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
        assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
        List<TacoUDT> tacos = fetchedOrder.getTacos();
        assertThat(tacos.size()).isEqualTo(2);
        assertThat(tacos).containsExactlyInAnyOrder(TacoUDRUtils.toTacoUDT(taco1), TacoUDRUtils.toTacoUDT(taco2));
    }

}