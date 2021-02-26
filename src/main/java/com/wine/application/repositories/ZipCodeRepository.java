package com.wine.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.domain.entities.ZipCodeRange;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCodeRange, Integer> {

	List<ZipCodeRange> findByIdNot(Integer id);

}
