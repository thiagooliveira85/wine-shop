package com.wine.domain.business;

import static com.wine.domain.MessagesBusiness.FINAL_DATA_INVALID;
import static com.wine.domain.MessagesBusiness.FINAL_RANGE_INVALID;
import static com.wine.domain.MessagesBusiness.FINAL_RANGE_NULL;
import static com.wine.domain.MessagesBusiness.INITIAL_DATA_INVALID;
import static com.wine.domain.MessagesBusiness.INITIAL_RANGE_GREATER_THAN;
import static com.wine.domain.MessagesBusiness.INITIAL_RANGE_INVALID;
import static com.wine.domain.MessagesBusiness.INITIAL_RANGE_NULL;

import java.math.BigInteger;

import com.wine.application.dtos.ZipCodeRangeDto;
import com.wine.domain.exceptions.BusinessException;
import com.wine.domain.utilities.StringUtils;

public class ZipCodeRangesValidator {
	
	public static void checkRanges(ZipCodeRangeDto dto) {
		
		if (StringUtils.checkIfIsBlankOrNull(dto.getBeginTrack()))
			throw new BusinessException(INITIAL_RANGE_NULL);
		
		if (StringUtils.checkIfIsBlankOrNull(dto.getEndTrack()))
			throw new BusinessException(FINAL_RANGE_NULL);
		
		if (!StringUtils.checkSizeField(dto.getBeginTrack(), 8))
			throw new BusinessException(INITIAL_RANGE_INVALID);
		
		if (!StringUtils.checkSizeField(dto.getEndTrack(), 8))
			throw new BusinessException(FINAL_RANGE_INVALID);
		
		if (checkInvalidDataField(dto.getBeginTrack()))
			throw new BusinessException(INITIAL_DATA_INVALID);
		
		if (checkInvalidDataField(dto.getEndTrack()))
			throw new BusinessException(FINAL_DATA_INVALID);
		
		BigInteger begin 	= new BigInteger(dto.getBeginTrack());
		BigInteger end 		= new BigInteger(dto.getEndTrack());
		
		if (begin.compareTo(end) >= 0)
			throw new BusinessException(INITIAL_RANGE_GREATER_THAN);
		
	}
	
	private static boolean checkInvalidDataField(String s) {
		try {
			new BigInteger(s);
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	

}
