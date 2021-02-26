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
import com.wine.domain.business.ZipCodeRangesValidator;

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
		ZipCodeRangesValidator.checkRanges(zipCodeDto);
		PhysicalStoreDto store = zipCodeDto.getStore();
		this.physicalStore = new PhysicalStore(store.getId(), store.getStoreCode(), store.getStoreName(), store.getAddress());
		this.beginTrack = new BigInteger(zipCodeDto.getBeginTrack());
		this.endTrack = new BigInteger(zipCodeDto.getEndTrack());
	}
	
	public ZipCodeRange(Integer id, ZipCodeRangeDto zipCodeDto) {
		ZipCodeRangesValidator.checkRanges(zipCodeDto);
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

}
