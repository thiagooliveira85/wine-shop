package com.wine.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.domain.entities.PhysicalStore;

@Repository
public interface PhysicalStoreRepository extends JpaRepository<PhysicalStore, Integer> {

}
