package com.ufrn.imd.web2.projeto01.livros.services.address;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Address;

@Service
public interface AddressService {
    public Address saveAddress(Address address);
    public void deleteAddressById(Integer id);
    public Address getAddressById(Integer id);
    public InfoAddressDTO getAddressDTOById(Integer id);
    public List<Address> getAddressList();
    public List<InfoAddressDTO> getAddressDTOList();
	public Address updatePutById(Integer currentAddressId, Address updateAddress);
	public Address updatePatchById(Integer currentAddressId, Address updateAddress);
}

