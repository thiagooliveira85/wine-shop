package com.wine.application.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import static com.wine.domain.MessagesBusiness.*;
import com.wine.domain.business.ZipCodeRangeConflicts;
import com.wine.domain.entities.ZipCodeRange;
import com.wine.domain.exceptions.RangeNotAllowedException;

@Service
public class ZipCodeRangeConflictsImpl implements ZipCodeRangeConflicts {


	@Override
	public void execute(ZipCodeRange range, List<ZipCodeRange> list) {
		
		if(list == null || list.isEmpty())
			return;
			
		BigInteger initialEntry = range.getBeginTrack();
		BigInteger finalEntry 	= range.getEndTrack();
			
		list.forEach(entity -> {
			BigInteger beginTrack = entity.getBeginTrack();
			BigInteger endTrack   = entity.getEndTrack();
			
			if (comparationOne(initialEntry, finalEntry, beginTrack)
					|| comparationTwo(initialEntry, finalEntry, beginTrack, endTrack)
					|| comparationThree(initialEntry, finalEntry, endTrack)) {
				
				throw new RangeNotAllowedException(RANGE_NOT_ALLOWED);
			}
		});
		
	}
	
	@Override
	public boolean checkIfItIsWithinItsOwnRange(ZipCodeRange newRecord, ZipCodeRange actualRecord) {
		
		BigInteger beginTrack 	= newRecord.getBeginTrack();
		BigInteger endTrack 	= newRecord.getEndTrack();
		BigInteger dbBeginTrack = actualRecord.getBeginTrack();
		BigInteger dbEndTrack 	= actualRecord.getEndTrack();
		
		if (beginTrack.compareTo(dbBeginTrack) >= 0 && beginTrack.compareTo(dbEndTrack) <= 0 
				&& endTrack.compareTo(dbEndTrack) <= 0 && endTrack.compareTo(dbBeginTrack) >= 0)
			return true;
		
		return false;
		
	}
	
	private boolean comparationOne(BigInteger initialEntry, BigInteger finalEntry, BigInteger beginTrack) {
		return beginTrack.compareTo(initialEntry) >= 0 && beginTrack.compareTo(finalEntry) <= 0;
	}
	
	private boolean comparationTwo(BigInteger initialEntry, BigInteger finalEntry, BigInteger beginTrack, BigInteger endTrack) {
		return beginTrack.compareTo(initialEntry) <= 0 && endTrack.compareTo(finalEntry) >= 0;
	}

	private boolean comparationThree(BigInteger initialEntry, BigInteger finalEntry, BigInteger endTrack) {
		return endTrack.compareTo(initialEntry) >= 0 && endTrack.compareTo(finalEntry) <= 0;
	}

}
