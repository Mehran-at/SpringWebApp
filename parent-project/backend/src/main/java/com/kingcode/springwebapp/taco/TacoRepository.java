package com.kingcode.springwebapp.taco;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.w3c.dom.html.HTMLMetaElement;

import java.util.List;

public interface TacoRepository
    extends PagingAndSortingRepository<Taco, Long> {

}
