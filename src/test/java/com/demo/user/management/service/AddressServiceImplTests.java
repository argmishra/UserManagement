package com.demo.user.management.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.demo.user.management.exception.AddressNotFoundException;
import com.demo.user.management.model.Address;
import com.demo.user.management.repo.IAddressRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserManagementApplication.class })
@Transactional
public class AddressServiceImplTests {

	@MockBean
	private IAddressRepository addressRepository;

	@Autowired
	private IAdressService addressService;

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
	public void updateAddress_success() {
		Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
		addressService.updateAddress(addressId, address);
		verify(addressRepository, times(1)).findById(addressId);
	}

	@Test
	public void updateAddress_fail() {
		Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.empty());
		assertThrows(AddressNotFoundException.class, () -> {
			addressService.updateAddress(addressId, address);
		});
	}

	@Test
	public void deleteAddress_success() {
		addressService.deleteAddress(addressId);
		verify(addressRepository, times(1)).deleteById(addressId);
	}

}
