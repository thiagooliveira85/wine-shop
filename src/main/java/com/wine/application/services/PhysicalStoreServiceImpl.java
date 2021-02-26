package com.wine.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wine.application.exceptions.PhysicalStoreNotFoundException;
import com.wine.application.repositories.PhysicalStoreRepository;
import com.wine.domain.entities.PhysicalStore;
import com.wine.domain.services.PhysicalStoreService;

@Service
public class PhysicalStoreServiceImpl implements PhysicalStoreService {
	
	@Autowired
	private PhysicalStoreRepository repository;

	@Override
	public PhysicalStore findById(Integer id) {
		Optional<PhysicalStore> optionalZipCode = this.repository.findById(id);
		if (!optionalZipCode.isPresent())
			throw new PhysicalStoreNotFoundException(String.format("Loja de id %s não cadastrada.", id));
		return optionalZipCode.get();
	}

	@Override
	public Page<PhysicalStore> findAll(Optional<Integer> page) {
		Integer pageNumber = page.isPresent() ? page.get() : 0;
		return this.repository.findAll(PageRequest.of(pageNumber, RECORDS_PER_PAGE));
	}


}
