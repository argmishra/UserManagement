package com.demo.user.management.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.user.management.UserManagementApplication;
import com.demo.user.management.model.Address;
import com.demo.user.management.service.IAdressService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserManagementApplication.class })
@Transactional
public class AddressControllerTests {

	@MockBean
	private IAdressService addressService;

	@Autowired
	private AdressController adressController;

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
	public void updateAddress() {
		adressController.updateAddress(addressId, address);
		verify(addressService, times(1)).updateAddress(addressId, address);
	}

	@Test
	public void deleteAddress() {
		adressController.deleteAddress(addressId);
		verify(addressService, times(1)).deleteAddress(addressId);
	}

}
