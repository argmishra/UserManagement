package com.demo.user.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.user.management.UserManagementApplication;
import com.demo.user.management.exception.PersonNotFoundException;
import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;
import com.demo.user.management.repo.IAddressRepository;
import com.demo.user.management.repo.IPersonRepository;
import com.demo.user.management.service.IPersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserManagementApplication.class })
@Transactional
public class PersonServiceImplTests {

	@MockBean
	private IPersonRepository personRepository;

	@MockBean
	private IAddressRepository addressRepository;

	@Autowired
	private IPersonService personService;

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
	public void createPerson_success() {
		Mockito.when(personRepository.save(person)).thenReturn(person);
		Person actualPerson = personService.createPerson(person);
		assertEquals(person.getFirstName(), actualPerson.getFirstName());
	}

	@Test
	public void updatePerson_success() {
		Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
		personService.updatePerson(personId, person);
		verify(personRepository, times(1)).findById(personId);
	}

	@Test
	public void updatePerson_fail() {
		Mockito.when(personRepository.findById(personId)).thenReturn(Optional.empty());
		assertThrows(PersonNotFoundException.class, () -> {
			personService.updatePerson(personId, person);
		});
	}

	@Test
	public void deletePerson_success() {
		personService.deletePerson(personId);
		verify(personRepository, times(1)).deleteById(personId);
	}

	@Test
	public void getAllPersons_success() {
		Mockito.when(personRepository.findAll()).thenReturn(List.of(person));
		List<Person> personList = personService.getAllPersons();

		assertEquals(1, personList.size());
		verify(personRepository, times(1)).findAll();
	}

	@Test
	public void countPersons_success() {
		Mockito.when(personRepository.count()).thenReturn(1L);
		Long count = personService.countPersons();

		assertEquals(1L, count);
		verify(personRepository, times(1)).count();
	}

	@Test
	public void createAddress_success() {
		Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));
		Mockito.when(addressRepository.save(address)).thenReturn(address);

		Address actualAddress = personService.createAddress(personId, address);
		assertEquals(address.getId(), actualAddress.getId());

		verify(personRepository, times(1)).findById(personId);
		verify(addressRepository, times(1)).save(address);

	}

}
