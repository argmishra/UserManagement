package com.demo.user.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.user.management.UserManagementApplication;
import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;
import com.demo.user.management.service.IPersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserManagementApplication.class })
@Transactional
public class PersonControllerTests {

	@MockBean
	private IPersonService personService;

	@Autowired
	private PersonController personController;

	private Person person = null;

	private Address address = null;

	private final long personId = 1L;

	private final String firstName = "Anurag";

	private final String lastName = "Mishra";

	@BeforeEach
	public void setup() {
		person = new Person();
		person.setId(personId);
		person.setFirstName(firstName);
		person.setLastName(lastName);

		address = new Address();
		address.setCity("Limerick");
		address.setPostalCode("V94");
		address.setState("Munster");
		address.setStreet("Sexton");
		address.setId(1L);
	}

	@Test
	public void createPerson() {
		Mockito.when(personService.createPerson(person)).thenReturn(person);
		ResponseEntity<Person> actualPerson = personController.createPerson(person);
		assertEquals(person.getFirstName(), actualPerson.getBody().getFirstName());
	}

	@Test
	public void updatePerson() {
		personController.updatePerson(personId, person);
		verify(personService, times(1)).updatePerson(personId, person);
	}

	@Test
	public void deletePerson() {
		personController.deletePerson(personId);
		verify(personService, times(1)).deletePerson(personId);
	}

	@Test
	public void getAllPersons() {
		Mockito.when(personService.getAllPersons()).thenReturn(List.of(person));
		ResponseEntity<List<Person>> personList = personController.getAllPersons();
		assertEquals(1, personList.getBody().size());

		verify(personService, times(1)).getAllPersons();
	}

	@Test
	public void countPersons() {
		Mockito.when(personService.countPersons()).thenReturn(1L);
		ResponseEntity<Long> count = personController.countPersons();
		assertEquals(1L, count.getBody().longValue());

		verify(personService, times(1)).countPersons();
	}

	@Test
	public void createAddress() {
		Mockito.when(personService.createAddress(personId, address)).thenReturn(address);

		ResponseEntity<Address> actualAddress = personController.createAddress(personId, address);
		assertEquals(address.getId(), actualAddress.getBody().getId());

		verify(personService, times(1)).createAddress(personId, address);
	}
}
