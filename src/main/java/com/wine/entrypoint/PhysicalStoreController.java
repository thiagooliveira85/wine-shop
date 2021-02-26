package com.wine.entrypoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.domain.entities.PhysicalStore;
import com.wine.domain.services.PhysicalStoreService;

@RestController
@RequestMapping("/physical-store")
public class PhysicalStoreController {
	
	@Autowired
	private PhysicalStoreService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<PhysicalStore>> getAll(@RequestParam Optional<Integer> page){
		return ResponseEntity.ok(this.service.findAll(page));
	}
	

}
