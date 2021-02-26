package com.wine.domain.entities;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wine.application.dtos.PhysicalStoreDto;
import com.wine.application.dtos.ZipCodeRangeDto;
import static com.wine.domain.MessagesBusiness.*;
import com.wine.domain.exceptions.BusinessException;
import com.wine.domain.utilities.StringUtils;

@Entity(name = "TB_FAIXA_CEP")
public class ZipCodeRange implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FAIXA_INICIO")
	private BigInteger beginTrack;
	
	@Column(name = "FAIXA_FIM")
	private BigInteger endTrack;
	
	@ManyToOne()
	@JoinColumn(name="CODIGO_LOJA")
	private PhysicalStore physicalStore;
	
	public ZipCodeRange() {}
	
	public ZipCodeRange(ZipCodeRangeDto zipCodeDto) {
		this.checkRanges(zipCodeDto);
		PhysicalStoreDto store = zipCodeDto.getStore();
		this.physicalStore = new PhysicalStore(store.getId(), store.getStoreCode(), store.getStoreName(), store.getAddress());
		this.beginTrack = new BigInteger(zipCodeDto.getBeginTrack());
		this.endTrack = new BigInteger(zipCodeDto.getEndTrack());
	}
	
	public ZipCodeRange(Integer id, ZipCodeRangeDto zipCodeDto) {
		this.checkRanges(zipCodeDto);
		PhysicalStoreDto store = zipCodeDto.getStore();
		this.id = id;
		this.physicalStore = new PhysicalStore(store.getId(), store.getStoreCode(), store.getStoreName(), store.getAddress());
		this.beginTrack = new BigInteger(zipCodeDto.getBeginTrack());
		this.endTrack = new BigInteger(zipCodeDto.getEndTrack());
	}
	
	public Integer getId() {
		return id;
	}

	public PhysicalStore getPhysicalStore() {
		return physicalStore;
	}

	public BigInteger getBeginTrack() {
		return beginTrack;
	}

	public BigInteger getEndTrack() {
		return endTrack;
	}

	private void checkRanges(ZipCodeRangeDto dto) {
		
		if (StringUtils.checkIfIsBlankOrNull(dto.getBeginTrack()))
			throw new BusinessException(INITIAL_RANGE_NULL);
		
		if (StringUtils.checkIfIsBlankOrNull(dto.getEndTrack()))
			throw new BusinessException(FINAL_RANGE_NULL);
		
		if (!StringUtils.checkSizeField(dto.getBeginTrack(), 8))
			throw new BusinessException(INITIAL_RANGE_INVALID);
		
		if (!StringUtils.checkSizeField(dto.getEndTrack(), 8))
			throw new BusinessException(FINAL_RANGE_INVALID);
		
		if (this.checkInvalidDataField(dto.getBeginTrack()))
			throw new BusinessException(INITIAL_DATA_INVALID);
		
		if (this.checkInvalidDataField(dto.getEndTrack()))
			throw new BusinessException(FINAL_DATA_INVALID);
		
		BigInteger begin 	= new BigInteger(dto.getBeginTrack());
		BigInteger end 		= new BigInteger(dto.getEndTrack());
		
		if (begin.compareTo(end) >= 0)
			throw new BusinessException(INITIAL_RANGE_GREATER_THAN);
		
	}
	
	private boolean checkInvalidDataField(String s) {
		try {
			new BigInteger(s);
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	
}
