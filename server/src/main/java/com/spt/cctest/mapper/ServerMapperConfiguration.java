package com.spt.cctest.mapper;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spt.cctest.api.model.CreditCard;
import com.spt.cctest.datasource.model.CreditCardEntity;

@Configuration
public class ServerMapperConfiguration {

    @Bean
    public ModelMapper createModelMapperBean() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true);

        PropertyMap<CreditCardEntity, CreditCard> cardMap = new PropertyMap<CreditCardEntity, CreditCard>() {
            @Override
            protected void configure() {
                map().getAccountLimit().setAmount(source.getAccountLimit());
                map().getAccountLimit().setCurrency(source.getCurrency());
                map().getAccountBalance().setAmount(source.getAccountBalance());
                map().getAccountBalance().setCurrency(source.getCurrency());
            }
        };

        PropertyMap<CreditCard, CreditCardEntity> entityMap = new PropertyMap<CreditCard, CreditCardEntity>() {
            @Override
            protected void configure() {
                map().setAccountLimit(source.getAccountLimit().getAmount());
                map().setCurrency(source.getAccountLimit().getCurrency());
                map().setAccountBalance(source.getAccountBalance().getAmount());
            }
        };
        modelMapper.addMappings(cardMap);
        modelMapper.addMappings(entityMap);
        return modelMapper;
    }
}
