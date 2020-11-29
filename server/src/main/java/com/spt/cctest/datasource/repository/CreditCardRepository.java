package com.spt.cctest.datasource.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spt.cctest.datasource.model.CreditCardEntity;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, UUID> {

}
