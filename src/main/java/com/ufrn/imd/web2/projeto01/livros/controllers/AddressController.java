package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;

    @GetMapping()
    public List<InfoAddressDTO> showAddressList() {
        return addressService.getAddressDTOList();
    };


    @PostMapping()
    public Address saveAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAddressById(@PathVariable Integer id){
        addressService.deleteAddressById(id);
    }

    @GetMapping("{id}")
    public InfoAddressDTO getAddressById(@PathVariable Integer id){
        return addressService.getAddressDTOById(id);
    }
    
   

    @PutMapping("{id}")
    public void updateAddress(@PathVariable Integer id, @RequestBody Address updatedAddress){
       addressService.updatePutById(id, updatedAddress);
    }

    @PatchMapping("{id}")
    public void updateAddressByPatch(@PathVariable Integer id, @RequestBody Address updatedAddress) {
        addressService.updatePatchById(id, updatedAddress);
    }
}
