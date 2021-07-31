package com.demo.user.management.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.user.management.exception.AddressNotFoundException;
import com.demo.user.management.model.Address;
import com.demo.user.management.repo.IAddressRepository;
import com.demo.user.management.service.IAdressService;

@Service
public class AddressServiceImpl implements IAdressService {

	@Autowired
	private IAddressRepository addressRepository;

	@Override
	public void updateAddress(long id, Address address) {
		Address dbAddress = this.getAddressById(id);
		BeanUtils.copyProperties(address, dbAddress);
		dbAddress.setId(id);
		addressRepository.save(dbAddress);
	}

	@Override
	public void deleteAddress(long id) {
		addressRepository.deleteById(id);
	}

	private Address getAddressById(long id) {
		Optional<Address> address = addressRepository.findById(id);
		if (!address.isPresent())
			throw new AddressNotFoundException("Address not found with " + id);
		return address.get();
	}

}
