package com.demo.user.management.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "addresses")
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private Long id;

	@Column(name = "street", nullable = false)
	@JsonProperty("street")
	private String street;

	@Column(name = "city", nullable = false)
	@JsonProperty("city")
	private String city;

	@Column(name = "state", nullable = false)
	@JsonProperty("state")
	private String state;

	@Column(name = "postal_code", nullable = false)
	@JsonProperty("postalCode")
	private String postalCode;

	@OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
	@JsonIgnore
	private Person person;

}
