package com.demo.user.management.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.user.management.exception.PersonNotFoundException;
import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;
import com.demo.user.management.repo.IAddressRepository;
import com.demo.user.management.repo.IPersonRepository;
import com.demo.user.management.service.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private IPersonRepository personRepository;

	@Autowired
	private IAddressRepository addressRepository;

	@Override
	public Person createPerson(Person person) {
		return personRepository.save(person);
	}

	@Override
	public void updatePerson(long id, Person person) {
		Person dbPerson = this.getPersonById(id);
		BeanUtils.copyProperties(person, dbPerson);
		dbPerson.setId(id);
		personRepository.save(dbPerson);
	}

	@Override
	public void deletePerson(long id) {
		personRepository.deleteById(id);
	}

	@Override
	public List<Person> getAllPersons() {
		return (List<Person>) personRepository.findAll();
	}

	@Override
	public long countPersons() {
		return personRepository.count();
	}

	@Override
	public Address createAddress(long id, Address address) {
		Person dbPerson = this.getPersonById(id);
		address.setPerson(dbPerson);
		return addressRepository.save(address);
	}

	private Person getPersonById(long id) {
		Optional<Person> person = personRepository.findById(id);
		if (!person.isPresent())
			throw new PersonNotFoundException("Person not found with " + id);
		return person.get();
	}

}
