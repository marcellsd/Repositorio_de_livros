package com.ufrn.imd.web2.projeto01.livros.services.address;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
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
        }).orElseThrow(() -> new NotFoundException("Address not found"));
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddressList() {
        return addressRepository.findAll();
    }

	@Override
	public Address updatePutById(Integer currentAddressId, Address updatedAddress) {
        updatedAddress.setId(currentAddressId);
		return saveAddress(updatedAddress);
	}


    @Override
    public Address updatePatchById(Integer currentAddressId, Address updatedAddress) {
        Address oldAddress = getAddressById(currentAddressId);
        updatedAddress.setId(oldAddress.getId());
        if(updatedAddress.getHqAddress() == null) updatedAddress.setHqAddress(oldAddress.getHqAddress());
        if(updatedAddress.getWebSiteAddress() == null) updatedAddress.setWebSiteAddress(oldAddress.getWebSiteAddress());
        if(updatedAddress.getPublisher() == null) updatedAddress.setPublisher(oldAddress.getPublisher());
        return saveAddress(updatedAddress);
    }

    @Override
    public InfoAddressDTO getAddressDTOById(Integer id) {
        Address address = addressRepository.findById(id).map(addressBD -> {
            return addressBD;
        }).orElseThrow(() -> new NotFoundException("Address not found"));
        
        InfoAddressDTO addressDTO = InfoAddressDTO.builder()
                                    .id(address.getId())
                                    .webSiteAddress(address.getWebSiteAddress())
                                    .hqAddress(address.getHqAddress())
                                    .publisherId(address.getPublisher().getId())
                                    .publisherName(address.getPublisher().getName())
                                    .build();
                                    
        return addressDTO;
    
    }

    @Override
    public List<InfoAddressDTO> getAddressDTOList() {
        List<Address> addresses = getAddressList();
        List<InfoAddressDTO> addressDTOs = new ArrayList<InfoAddressDTO>();

        for (Address address : addresses) {
            InfoAddressDTO addressDTO = InfoAddressDTO.builder()
                                    .id(address.getId())
                                    .webSiteAddress(address.getWebSiteAddress())
                                    .hqAddress(address.getHqAddress())
                                    .publisherId(address.getPublisher().getId())
                                    .publisherName(address.getPublisher().getName())
                                    .build();

            addressDTOs.add(addressDTO);
        }
        
        return addressDTOs;
    }
    
}
