package com.demo.user.management.service;

import java.util.List;

import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;

public interface IPersonService {

	public Person createPerson(Person person);

	public void updatePerson(long id, Person person);

	public void deletePerson(long id);

	public List<Person> getAllPersons();

	public long countPersons();

	public Address createAddress(long id, Address address);

}
