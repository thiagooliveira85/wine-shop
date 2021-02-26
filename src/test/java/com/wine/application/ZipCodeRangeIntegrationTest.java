package com.wine.application;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wine.application.dtos.PhysicalStoreDto;
import com.wine.application.dtos.ZipCodeRangeDto;
import com.wine.application.exceptions.PhysicalStoreNotFoundException;
import com.wine.application.exceptions.ZipCodeRangeNotFoundException;
import com.wine.domain.entities.ZipCodeRange;
import com.wine.domain.services.PhysicalStoreService;
import com.wine.domain.services.ZipCodeRanges;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipCodeRangeIntegrationTest {
	
	@Autowired
	private ZipCodeRanges zipCodeRanges;
	
	@Autowired
	private PhysicalStoreService physicalService;
	
	// VALIDATIONS
	
	@Test
	void shouldLaunchExceptionZipCodeRangeNotFound() {
		assertThrows(ZipCodeRangeNotFoundException.class, () -> {
			this.zipCodeRanges.findById(0);
		});
	}
	
	@Test
	void mustLaunchExceptionExchangeNonFound() {
		assertThrows(PhysicalStoreNotFoundException.class, () -> {
			this.physicalService.findById(0);
		});
	}
	
	
	
	// CRUDS
	
	@Test
	void mustRegisterZipCodeFields() {
		ZipCodeRange save = CreateZipRange("10000000", "20000000");
		assertTrue(save != null);
	}
	
	@Test
	void mustFindARangeOfRegisteredZipCode() {
		ZipCodeRange save = CreateZipRange("20000001", "30000000");
		ZipCodeRange find = zipCodeRanges.findById(save.getId());
		assertTrue(save.getId() == find.getId());
	}
	
	@Test
	void mustRefreshZipCodeForExpected() {
		ZipCodeRange saved = CreateZipRange("88000001", "99000000");
		assertTrue(saved != null);
		ZipCodeRangeDto zipCodeDto = this.createZipCodeDto("88000010", "99000010");
		ZipCodeRange updated = this.zipCodeRanges.update(saved.getId(), zipCodeDto);
		
		assertTrue(updated.getBeginTrack().equals(new BigInteger("88000010")) 
				&& updated.getEndTrack().equals(new BigInteger("99000010")));
	}
	
	@Test
	void mustDeleteZipCodeRange() {
		ZipCodeRange saved = CreateZipRange("70000001", "80000000");
		assertTrue(saved != null);
		this.zipCodeRanges.delete(saved.getId());
		assertThrows(ZipCodeRangeNotFoundException.class, () -> {
			this.zipCodeRanges.findById(saved.getId());
		});
	}
	
	
	
	// PRIVATE METHODS
	
	private ZipCodeRange CreateZipRange(String beginTrack, String endTrack) {
		ZipCodeRangeDto zipCodeDto = createZipCodeDto(beginTrack, endTrack);
		ZipCodeRange save = zipCodeRanges.save(zipCodeDto);
		return save;
	}

	private ZipCodeRangeDto createZipCodeDto(String beginTrack, String endTrack) {
		PhysicalStoreDto store = new PhysicalStoreDto(1);
		ZipCodeRangeDto zipCodeDto = new ZipCodeRangeDto(store, beginTrack, endTrack);
		return zipCodeDto;
	}
}
