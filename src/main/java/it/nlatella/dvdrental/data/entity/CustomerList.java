package it.nlatella.dvdrental.data.entity;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;
import javax.persistence.*;


/**
 * The persistent class for the customer_list database table.
 * 
 */
@Entity
@Immutable
@Table(name="customer_list")
@NamedQuery(name="CustomerList.findAll", query="SELECT c FROM CustomerList c")
public class CustomerList implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String city;

	private String country;

	@Id
	private Integer id;

	private String name;

	private String notes;

	private String phone;

	private Integer sid;

	@Column(name="\"zip code\"")
	private String zip_code;

	public CustomerList() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getZip_code() {
		return this.zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

}