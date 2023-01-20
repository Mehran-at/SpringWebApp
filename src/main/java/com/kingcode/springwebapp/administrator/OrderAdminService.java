package com.kingcode.springwebapp.administrator;

import com.kingcode.springwebapp.tacoorder.TacoOrder;
import com.kingcode.springwebapp.tacoorder.TacoOrderRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OrderAdminService {

    private TacoOrderRepository orderRepository;

    public OrderAdminService(TacoOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }


    /**
     * suppose we have a method that fetches an order by its ID.
     * If you want to restrict it from being used except by admins or by the user who the order belongs to,
     * you can use @PostAuthorize like this:
     */
    @PostAuthorize("hasRole('ADMIN') || " + "returnObject.user.username == authentication.name")
    public TacoOrder getOrder(long id) {
        return null;
    }
}
