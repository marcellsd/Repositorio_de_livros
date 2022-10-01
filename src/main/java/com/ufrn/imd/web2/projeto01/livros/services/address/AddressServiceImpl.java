package com.ufrn.imd.web2.projeto01.livros.services.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.repositories.AddressRepository;

@Component
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public void deleteAddressById(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getAddressById(Integer id) {
        
        return addressRepository.findById(id).map(address -> {
            return address;
        }).orElseThrow();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddresssList() {
        return addressRepository.findAll();
    }

	@Override
	public Address updateById(Integer currentAddressId, Address newAddress) {
        newAddress.setId(currentAddressId);
		return addressRepository.save(newAddress);
	}
    
}
