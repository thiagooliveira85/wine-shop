package com.wine.entrypoint;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wine.application.dtos.ZipCodeRangeDto;
import static com.wine.domain.MessagesBusiness.*;
import com.wine.domain.entities.ZipCodeRange;
import com.wine.domain.services.ZipCodeRanges;

@RestController
@RequestMapping("/zipcode/ranges")
public class ZipCodeController {
	
	@Autowired
	private ZipCodeRanges zipCodeRanges;
	
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody ZipCodeRangeDto zipCodeDto) throws URISyntaxException{
		ZipCodeRange saved = this.zipCodeRanges.save(zipCodeDto);		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(String.format(CREATE_OK_ZIP_CODE_RANGE, saved.getId()));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ZipCodeRange>> getAll(@RequestParam Optional<Integer> page){
		return ResponseEntity.ok(this.zipCodeRanges.findAll(page));
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipCodeRange> getById(@PathVariable Integer id){
		return ResponseEntity.ok(this.zipCodeRanges.findById(id));
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipCodeRange> updateAll(@PathVariable Integer id, @RequestBody ZipCodeRangeDto zipCodeDto) {
		ZipCodeRange updated = this.zipCodeRanges.update(id, zipCodeDto);
		return  ResponseEntity.ok(updated);
	}
	
	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZipCodeRange> update(@PathVariable Integer id, @RequestBody ZipCodeRangeDto zipCodeDto) {
		ZipCodeRange updated = this.zipCodeRanges.update(id, zipCodeDto);
		return  ResponseEntity.ok(updated);
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		this.zipCodeRanges.delete(id);
		return  ResponseEntity.status(HttpStatus.OK)
				.body(String.format(DELETE_OK_ZIP_CODE_RANGE, id));
	}
}
