package com.kingcode.springwebapp.tacoorder;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, UUID> {
}
