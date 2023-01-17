package com.kingcode.springwebapp.tacoorder;

import org.springframework.data.repository.CrudRepository;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, String> {
}
