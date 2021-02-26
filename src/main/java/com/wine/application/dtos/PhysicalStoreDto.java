package com.wine.application.dtos;

public class PhysicalStoreDto {
	
	private Integer id;
	private String storeCode;
	private String storeName;
	private String address;
	

	public PhysicalStoreDto(Integer id) {
		super();
		this.id = id;
	}

	public PhysicalStoreDto(String storeCode, String storeName, String address) {
		this.storeCode = storeCode;
		this.storeName = storeName;
		this.address = address;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getStoreCode() {
		return storeCode;
	}
	
	public String getStoreName() {
		return storeName;
	}
	
	public String getAddress() {
		return address;
	}

}
