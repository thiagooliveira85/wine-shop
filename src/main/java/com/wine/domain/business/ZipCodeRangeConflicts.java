package com.wine.domain.business;

import java.util.List;

import com.wine.domain.entities.ZipCodeRange;

public interface ZipCodeRangeConflicts {
	
	void execute(ZipCodeRange ZipCodeRange, List<ZipCodeRange> listZipCodeRange);

	boolean checkIfItIsWithinItsOwnRange(ZipCodeRange newRecord, ZipCodeRange actualRecord);

}
