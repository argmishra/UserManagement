package com.demo.user.management.interation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.user.management.UserManagementApplication;
import com.demo.user.management.model.Address;
import com.demo.user.management.model.Person;
import com.demo.user.management.service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserManagementApplication.class)
@AutoConfigureMockMvc
public class PersonControllerIntegrationTests {

	@MockBean
	private IPersonService personService;

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

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
	public void createPerson_pass() throws Exception {
		Mockito.when(personService.createPerson(person)).thenReturn(person);

		mockMvc.perform(
				post("/persons").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(person)))
				.andExpect(status().isCreated());
	}

	@Test
	public void createPerson_fail_wrong_body() throws Exception {
		mockMvc.perform(
				post("/persons").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createPerson_fail_no_body() throws Exception {
		mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	public void updatePerson_pass() throws Exception {
		mockMvc.perform(put("/persons/" + personId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(person))).andExpect(status().isNoContent());
	}

	@Test
	public void updatePerson_fail_empty_body() throws Exception {
		mockMvc.perform(put("/persons/" + personId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(null))).andExpect(status().isBadRequest());
	}

	@Test
	public void updatePerson_fail_no_body() throws Exception {
		mockMvc.perform(put("/persons/" + personId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deletePerson() throws Exception {
		mockMvc.perform(delete("/persons/" + personId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void getAllPersons() throws Exception {
		Mockito.when(personService.getAllPersons()).thenReturn(List.of(person));
		mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void countPersons() throws Exception {
		Mockito.when(personService.countPersons()).thenReturn(1L);
		mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void createAddress_pass() throws Exception {
		Mockito.when(personService.createAddress(personId, address)).thenReturn(address);

		mockMvc.perform(post("/persons/" + personId + "/address").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(address))).andExpect(status().isCreated());
	}

	@Test
	public void createAddress_fail_no_body() throws Exception {
		mockMvc.perform(post("/persons/" + personId + "/address").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(null))).andExpect(status().isBadRequest());
	}

	@Test
	public void createAddress_fail_empty_body() throws Exception {
		mockMvc.perform(post("/persons/" + personId + "/address").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
