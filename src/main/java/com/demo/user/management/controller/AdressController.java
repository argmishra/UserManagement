package com.demo.user.management.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.user.management.model.Address;
import com.demo.user.management.service.IAdressService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("addresses")
@Slf4j
@Validated
public class AdressController {

	@Autowired
	private IAdressService adressService;

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateAddress(@PathVariable long id, @Valid @RequestBody Address address) {
		log.info("Update Address by id {} ", id);
		adressService.updateAddress(id, address);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable long id) {
		log.info("Delete Address by id {} ", id);
		adressService.deleteAddress(id);
		return ResponseEntity.noContent().build();
	}

}
