package com.kingcode.springwebapp.config;

import com.datastax.oss.driver.api.core.session.Session;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Objects;
import java.util.logging.Logger;

@Configuration
@EnableConfigurationProperties(CassandraProperties.class)
@EnableCassandraRepositories
public class CassandraConfiguration {

    @Autowired
    private Environment environment;
    @Bean
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints("localhost");
        session.setKeyspaceName("main_keyspace");
        session.setUsername("cassandra");
        session.setPassword("cassandra");
        session.setLocalDatacenter("dc1");
        return session;
    }

    @Bean
    public CassandraMappingContext mappingContext() {
        return new BasicCassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraOperations cassandraTemplate() throws Exception {
        return new CassandraTemplate(Objects.requireNonNull(session().getObject()));
    }
}
