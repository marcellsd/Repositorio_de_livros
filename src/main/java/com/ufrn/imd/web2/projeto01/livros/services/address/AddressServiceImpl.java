package com.ufrn.imd.web2.projeto01.livros.services.address;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressDTO;
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
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
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
	public Address updateById(Integer currentAddressId, Address newAddress) {
        newAddress.setId(currentAddressId);
		return addressRepository.save(newAddress);
	}

    @Override
    public InfoAddressDTO getAddressDTOById(Integer id) {
        Address address = addressRepository.findById(id).map(addressBD -> {
            return addressBD;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado"));
        
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
