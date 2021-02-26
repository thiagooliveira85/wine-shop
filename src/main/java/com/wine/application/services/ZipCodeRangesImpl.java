package com.wine.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wine.application.dtos.ZipCodeRangeDto;
import com.wine.application.exceptions.PhysicalStoreNotFoundException;
import com.wine.application.exceptions.ZipCodeRangeNotFoundException;
import com.wine.application.repositories.PhysicalStoreRepository;
import com.wine.application.repositories.ZipCodeRepository;
import com.wine.domain.business.ZipCodeRangeConflicts;
import com.wine.domain.entities.ZipCodeRange;
import com.wine.domain.services.ZipCodeRanges;

@Service
public class ZipCodeRangesImpl implements ZipCodeRanges {
	
	@Autowired
	private ZipCodeRepository repository;
	
	@Autowired
	private PhysicalStoreRepository physicalStoreRepository;	
	
	@Autowired
	private ZipCodeRangeConflicts zipCodeRangeConflicts;

	@Override
	public ZipCodeRange save(ZipCodeRangeDto zipCodeRangeDto) {
		Integer physicalStoreId = zipCodeRangeDto.getStore().getId();
		this.checkIdPhysicalStoreInDataBase(physicalStoreId);
		ZipCodeRange entity = new ZipCodeRange(zipCodeRangeDto);
		this.zipCodeRangeConflicts.execute(entity, this.findAll());
		return this.repository.save(entity);
	}

	@Override
	public ZipCodeRange findById(Integer id) {
		Optional<ZipCodeRange> optionalZipCode = this.repository.findById(id);
		if (!optionalZipCode.isPresent())
			throw new ZipCodeRangeNotFoundException(String.format("Faixa de CEP com id %s não encontrado.", id));
		return optionalZipCode.get();
	}

	@Override
	public ZipCodeRange update(Integer id, ZipCodeRangeDto zipCodeRangeDto) {
		checkId(id);
		this.checkIdZipCodeRangeInDataBase(id);
		ZipCodeRange newRecord = new ZipCodeRange(id, zipCodeRangeDto);
		boolean isTrue = this.checkIfItIsWithinItsOwnRange(newRecord, this.findById(id));
		if(!isTrue) {
			this.zipCodeRangeConflicts.execute(newRecord, this.findByIdNot(id));
		}
		return this.repository.save(newRecord);
	}

	private List<ZipCodeRange> findByIdNot(Integer id) {
		return this.repository.findByIdNot(id);
	}

	@Override
	public void delete(Integer id) {
		checkId(id);
		ZipCodeRange find = this.findById(id);
		this.repository.delete(find);
	}

	@Override
	public Page<ZipCodeRange> findAll(Optional<Integer> page) {
		Integer pageNumber = page.isPresent() ? page.get() : 0;
		return this.repository.findAll(PageRequest.of(pageNumber, RECORDS_PER_PAGE));
	}
	
	@Override
	public List<ZipCodeRange> findAll() {
		return this.repository.findAll();
	}
	
	private void checkId(Integer id) {
		if (id == null)
			throw new IllegalArgumentException("Id não encontrado para a remoção da faixa de CEP!");
	}
	
	private void checkIdPhysicalStoreInDataBase(Integer id) {
		boolean existsById = this.physicalStoreRepository.existsById(id);
		if(!existsById)
			throw new PhysicalStoreNotFoundException(String.format("Loja de id %s não cadastrada.", id));
	}
	
	private void checkIdZipCodeRangeInDataBase(Integer id) {
		boolean existsById = this.repository.existsById(id);
		if(!existsById)
			throw new PhysicalStoreNotFoundException(String.format("Faixa de CEPs de id %s não cadastrada.", id));
	}
	
	private boolean checkIfItIsWithinItsOwnRange(ZipCodeRange newRecord, ZipCodeRange actualRecord) {
		return this.zipCodeRangeConflicts.checkIfItIsWithinItsOwnRange(newRecord, actualRecord);
	}

}
