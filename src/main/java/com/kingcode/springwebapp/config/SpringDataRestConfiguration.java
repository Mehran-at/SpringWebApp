package com.kingcode.springwebapp.config;

import com.kingcode.springwebapp.taco.Taco;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;


@Configuration
public class SpringDataRestConfiguration {

    @Bean
    public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>> tacoProcessor(EntityLinks links) {
        return resource -> {
            resource.add(
                links.linkFor(Taco.class)
                    .slash("recent")
                    .withRel("recents"));
            return resource;
        };
    }

}
