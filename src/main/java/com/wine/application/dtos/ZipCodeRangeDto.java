package com.wine.application.dtos;

public class ZipCodeRangeDto {

	private Integer id;
	private PhysicalStoreDto store;
	private String beginTrack;
	private String endTrack;
	
	
	public ZipCodeRangeDto() {}

	public ZipCodeRangeDto(Integer id) {
		this.id = id;
	}

	public ZipCodeRangeDto(Integer id, PhysicalStoreDto store, String beginTrack, String endTrack) {
		this.id = id;
		this.store = store;
		this.beginTrack = beginTrack;
		this.endTrack = endTrack;
	}
	
	public ZipCodeRangeDto(PhysicalStoreDto store, String beginTrack, String endTrack) {
		this.store = store;
		this.beginTrack = beginTrack;
		this.endTrack = endTrack;
	}
	
	public Integer getId() {
		return id;
	}
	
	public PhysicalStoreDto getStore() {
		return store;
	}
	
	public String getBeginTrack() {
		return beginTrack;
	}
	
	public String getEndTrack() {
		return endTrack;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
