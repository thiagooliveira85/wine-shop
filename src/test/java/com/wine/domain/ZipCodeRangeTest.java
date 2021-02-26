package com.wine.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wine.application.dtos.PhysicalStoreDto;
import com.wine.application.dtos.ZipCodeRangeDto;
import com.wine.application.services.ZipCodeRangeConflictsImpl;
import com.wine.domain.business.ZipCodeRangeConflicts;
import com.wine.domain.entities.ZipCodeRange;
import com.wine.domain.exceptions.BusinessException;
import com.wine.domain.exceptions.RangeNotAllowedException;

import static com.wine.domain.MessagesBusiness.*;

class ZipCodeRangeTest {
	
	List<ZipCodeRange> listRange = null;
	ZipCodeRangeConflicts conflicts = null;

	@BeforeEach
	private void init() {
		conflicts = new ZipCodeRangeConflictsImpl();		
		listRange = new ArrayList<>();
		listRange.add(this.createZipCodeRange("10000001", "20000000"));
		listRange.add(this.createZipCodeRange("20000001", "30000000"));
		listRange.add(this.createZipCodeRange("30000001", "40000000"));
		listRange.add(this.createZipCodeRange("40000001", "50000000"));
		listRange.add(this.createZipCodeRange("50000001", "60000000"));
	}

	@Test
	void mustReturnExceptionExitInitialStripEmpty() {
		ZipCodeRangeDto dto = this.createZipCodeDto("", "10000000");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(INITIAL_RANGE_NULL, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionExtractFinalEmpty() {
		ZipCodeRangeDto dto = this.createZipCodeDto("10000000", "");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(FINAL_RANGE_NULL, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionOfInitialBiggerThanOrSameAsTheFinal() {
		ZipCodeRangeDto dto = this.createZipCodeDto("20000000", "10000000");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(INITIAL_RANGE_GREATER_THAN, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionBeginningBiggerThanFinal() {
		ZipCodeRangeDto dto = this.createZipCodeDto("50000000", "50000000");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(INITIAL_RANGE_GREATER_THAN, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionInitialFieldInvalid() {
		ZipCodeRangeDto dto = this.createZipCodeDto("45605", "10000000");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(INITIAL_RANGE_INVALID, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionFinalFinalInvalid() {
		ZipCodeRangeDto dto = this.createZipCodeDto("20000000", "123");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(FINAL_RANGE_INVALID, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionOfInitialWithAlphanumerics() {
		ZipCodeRangeDto dto = this.createZipCodeDto("200GT000", "20000100");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(INITIAL_DATA_INVALID, error.getMessage());
	}
	
	@Test
	void mustReturnExceptionExpressionFinalWithAlphanumerics() {
		ZipCodeRangeDto dto = this.createZipCodeDto("20000100", "200H%000");
		BusinessException error = assertThrows(BusinessException.class, () -> {
			new ZipCodeRange(dto);
		});
		assertEquals(FINAL_DATA_INVALID, error.getMessage());
	}
	
	@Test
	void mustReturnTrueWithinProperRange1() {
		ZipCodeRange dbRecord = this.createZipCodeRange("20000000", "30000000");
		ZipCodeRange newRecord = this.createZipCodeRange("20000001", "29999999");
		boolean check = conflicts.checkIfItIsWithinItsOwnRange(newRecord, dbRecord);
		assertTrue(check);
	}
	
	@Test
	void mustReturnTrueWithinProperRange2() {
		ZipCodeRange dbRecord = this.createZipCodeRange("20000000", "30000000");
		ZipCodeRange newRecord = this.createZipCodeRange("20000000", "29999999");
		boolean check = conflicts.checkIfItIsWithinItsOwnRange(newRecord, dbRecord);
		assertTrue(check);
	}
	
	@Test
	void mustReturnTrueWithinProperRange3() {
		ZipCodeRange dbRecord = this.createZipCodeRange("20000000", "30000000");
		ZipCodeRange newRecord = this.createZipCodeRange("20000001", "30000000");
		boolean check = conflicts.checkIfItIsWithinItsOwnRange(newRecord, dbRecord);
		assertTrue(check);
	}
	
	@Test
	void mustReturnFalseOutsideRange() {
		ZipCodeRange dbRecord = this.createZipCodeRange("20000000", "30000000");
		ZipCodeRange newRecord = this.createZipCodeRange("20000001", "30000001");
		boolean check = conflicts.checkIfItIsWithinItsOwnRange(newRecord, dbRecord);
		assertTrue(!check);
	}
	
	
	@Test
	void mustReturnExceptionRangeInRange() {
		check("10000002", "50000001");
	}
	
	@Test
	void mustReturnExceptionOfRangeOfRange() {
		check("10000000", "60000001");
	}
	
	@Test
	void mustReturnExecuteFieldWithinTheRange() {
		check("50000555", "60000001");
	}
	
	@Test
	void mustReturnExchangeFieldInRangeFromEnd() {
		check("10000000", "10000002");
	}
	
	@Test
	void mustReturnExchangeFieldInRangesDistinct() {
		check("10000555", "20000555");
	}
	
	
	
	// PRIVATE METHODS
	
	private ZipCodeRangeDto createZipCodeDto(String beginTrack, String endTrack) {
		PhysicalStoreDto store = new PhysicalStoreDto(1);
		ZipCodeRangeDto zipCodeDto = new ZipCodeRangeDto(store, beginTrack, endTrack);
		return zipCodeDto;
	}
	
	private ZipCodeRange createZipCodeRange(String beginTrack, String endTrack) {
		return new ZipCodeRange(this.createZipCodeDto(beginTrack, endTrack));
	}
	
	private void check(String beginTrack, String endTrack) {
		RuntimeException error = assertThrows(RangeNotAllowedException.class, () -> {
			conflicts.execute(this.createZipCodeRange(beginTrack, endTrack), this.listRange);
		});
		assertEquals(RANGE_NOT_ALLOWED, error.getMessage());
	}

}
