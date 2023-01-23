package com.kingcode.springwebapp;

import com.kingcode.springwebapp.administrator.OrderAdminService;
import com.kingcode.springwebapp.discounts.DiscountCodeProps;
import com.kingcode.springwebapp.ingredient.IngredientRepository;
import com.kingcode.springwebapp.taco.TacoRepository;
import com.kingcode.springwebapp.tacoorder.OrderProps;
import com.kingcode.springwebapp.tacoorder.TacoOrderRepository;
import com.kingcode.springwebapp.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest // (secure=false)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Note: Most of these mocks are here to avoid autowiring issues. They aren't
    //       actually used in the course of the home page test, so their behavior
    //       isn't important. They just need to exist so autowiring can take place.

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository designRepository;

    @MockBean
    private TacoOrderRepository orderRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private DiscountCodeProps discountProps;

    @MockBean
    private OrderProps orderProps;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(content().string(
                containsString("Welcome to...")));
    }

}