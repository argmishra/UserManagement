package com.demo.user.management.service;

import com.demo.user.management.model.Address;

public interface IAdressService {
	public void updateAddress(long id, Address address);

	public void deleteAddress(long id);

}
