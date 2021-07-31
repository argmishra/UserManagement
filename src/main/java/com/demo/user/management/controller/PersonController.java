package com.demo.user.management.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;
import com.demo.user.management.service.IPersonService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("persons")
@Slf4j
@Validated
public class PersonController {

	@Autowired
	private IPersonService personService;

	@PostMapping
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		log.info("Save Person");
		return new ResponseEntity<>(personService.createPerson(person), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updatePerson(@PathVariable long id, @Valid @RequestBody Person person) {
		log.info("Get Person by id {} ", id);
		personService.updatePerson(id, person);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable long id) {
		log.info("Delete Person by id {} ", id);
		personService.deletePerson(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Person>> getAllPersons() {
		log.info("Get All Persons");
		return new ResponseEntity<List<Person>>(personService.getAllPersons(), HttpStatus.OK);
	}

	@GetMapping("count")
	public ResponseEntity<Long> countPersons() {
		log.info("Get number of Persons");
		return new ResponseEntity<Long>(personService.countPersons(), HttpStatus.OK);
	}

	@PostMapping("{id}/address")
	public ResponseEntity<Address> createAddress(@PathVariable long id, @Valid @RequestBody Address address) {
		log.info("Save Address for id {} ", id);
		return new ResponseEntity<>(personService.createAddress(id, address), HttpStatus.CREATED);

	}

}
