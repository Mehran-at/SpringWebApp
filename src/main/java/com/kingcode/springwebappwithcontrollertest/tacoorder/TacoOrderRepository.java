package com.kingcode.springwebappwithcontrollertest.tacoorder;

import org.springframework.data.repository.CrudRepository;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {
}
