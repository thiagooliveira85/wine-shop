package com.wine.domain.services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.wine.domain.entities.PhysicalStore;

public interface PhysicalStoreService {
	
	static final int RECORDS_PER_PAGE = 5;
	
	PhysicalStore findById(Integer id);
	
	Page<PhysicalStore> findAll(Optional<Integer> page);

}
