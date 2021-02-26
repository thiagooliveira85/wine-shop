package com.wine.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "TB_LOJA")
public class PhysicalStore implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CODIGO")
	private String code;
	
	@Column(name = "NOME")
	private String name;
	
	@Column(name = "ENDERECO")
	private String address;
	
	@JsonIgnore
	@OneToMany(mappedBy="physicalStore", cascade=CascadeType.ALL)
	private List<ZipCodeRange> zipCodes;
	
	public PhysicalStore() {}
	
	public PhysicalStore(Integer id, String code, String name, String address) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.zipCodes = new ArrayList<>();
	}
	
	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public void addZipCodes(ZipCodeRange zipcode) {
		this.zipCodes.add(zipcode);
	}
	
}
