package com.ufrn.imd.web2.projeto01.livros.services.address;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.models.Address;

@Service
public interface AddressService {
    public Address saveAddress(Address address);
    public void deleteAddressById(Integer id);
    public Address getAddressById(Integer id);
    public List<Address> getAddresssList();
	public Address updateById(Integer currentAddressId, Address newAddress);
}

