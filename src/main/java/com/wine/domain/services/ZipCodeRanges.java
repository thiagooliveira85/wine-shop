package com.wine.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.wine.application.dtos.ZipCodeRangeDto;
import com.wine.domain.entities.ZipCodeRange;

public interface ZipCodeRanges {
	
	static final int RECORDS_PER_PAGE = 5;
	
	ZipCodeRange save(ZipCodeRangeDto zipCodeDto);
	
	ZipCodeRange findById(Integer id);
	
	ZipCodeRange update(Integer id, ZipCodeRangeDto zipCodeDto);
	
	void delete(Integer zipCodeDto);

	Page<ZipCodeRange> findAll(Optional<Integer> page);
	
	List<ZipCodeRange> findAll();

}
