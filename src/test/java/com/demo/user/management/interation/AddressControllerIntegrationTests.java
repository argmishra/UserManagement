package com.demo.user.management.interation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.user.management.UserManagementApplication;
import com.demo.user.management.model.Address;
import com.demo.user.management.service.IAdressService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserManagementApplication.class)
@AutoConfigureMockMvc
public class AddressControllerIntegrationTests {

	@MockBean
	private IAdressService addressService;

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	private Address address = null;

	private final long addressId = 1L;

	@BeforeEach
	public void setup() {
		address = new Address();
		address.setCity("Limerick");
		address.setPostalCode("V94");
		address.setState("Munster");
		address.setStreet("Sexton");
		address.setId(addressId);
	}

	@Test
	public void updatePerson_pass() throws Exception {
		mockMvc.perform(put("/addresses/" + addressId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(address))).andExpect(status().isNoContent());
	}

	@Test
	public void updatePerson_fail_empty_body() throws Exception {
		mockMvc.perform(put("/addresses/" + addressId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(null))).andExpect(status().isBadRequest());
	}

	@Test
	public void updatePerson_fail_no_body() throws Exception {
		mockMvc.perform(put("/addresses/" + addressId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deletePerson() throws Exception {
		mockMvc.perform(delete("/addresses/" + addressId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}
