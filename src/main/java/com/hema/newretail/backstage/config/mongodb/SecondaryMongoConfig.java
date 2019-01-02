package com.hema.newretail.backstage.config.mongodb;

import com.hema.newretail.backstage.common.mongodb.BigDecimalToDecimal128Converter;
import com.hema.newretail.backstage.common.mongodb.Decimal128ToBigDecimalConverter;
import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangHaiSheng
 */
@Configuration
public class SecondaryMongoConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb.secondary")
    public MongoProperties secondaryMongoProperties() {
        MongoProperties mongoProperties = new MongoProperties();
        mongoProperties.setUri("uri");
        return mongoProperties;
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() throws Exception {
        return new MongoTemplate(secondaryFactory(), customConversions());
    }

    @Bean
    public MongoDbFactory secondaryFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(secondaryMongoProperties().getUri()));
    }

    @Bean
    public MappingMongoConverter customConversions() throws Exception {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.secondaryFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new BigDecimalToDecimal128Converter());
        converterList.add(new Decimal128ToBigDecimalConverter());
        converter.setCustomConversions(new CustomConversions(converterList));
        return converter;
    }
}