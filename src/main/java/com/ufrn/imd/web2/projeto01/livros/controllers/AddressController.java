package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;
@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;
    Integer currentAddressId = null;

    @RequestMapping("/getAddresssList")
    public String showListaCursos(Model model){
        List<Address> addresss = addressService.getAddresssList();
        model.addAttribute("addresss",addresss);
        return "address/addressList";
    }

    @RequestMapping("/showFormAddress")
    public String showFormCurso(Model model){
        model.addAttribute("address", new Address());
        return "address/formAddress";
    }

    @RequestMapping("/addAddress")
    public String addAddress(@ModelAttribute("address") Address address, Model model){
        Address newAddress = addressService.saveAddress(address);
        model.addAttribute("address", newAddress);
        return "address/addAddressPage";
    }
    
    @RequestMapping("/deleteAddress/{addressId}")
    public String deleteAddress(@PathVariable String addressId, Model model){
        Integer id = Integer.parseInt(addressId);
        Address address =  addressService.getAddressById(id);
        addressService.deleteAddressById(id);
        model.addAttribute("address",address);
        return "address/deleteAddressPage";
    }

    @RequestMapping("/getAddress/{addressId}")
    public String getAddressById(@PathVariable String addressId, Model model){
        Integer id = Integer.parseInt(addressId);
        Address address =  addressService.getAddressById(id);
        model.addAttribute("address", address);
        return "address/addressPage";
    }
    
    @RequestMapping("/showFormAddressUpdate/{addressId}")
    public String showFormAddressUpdate(@PathVariable String addressId, @ModelAttribute("address") Address address, Model model){
        Integer id = Integer.parseInt(addressId);
        this.currentAddressId = id;
        address =  addressService.getAddressById(id);
        model.addAttribute("Address", address);
        return "address/formUpdateAddress";
    }

    @RequestMapping("/updateAddress")
    public String updateAddress(@ModelAttribute("Address") Address newAddress, Model model){
        Address address = addressService.updateById(currentAddressId,newAddress);
        model.addAttribute("addressAtualizado", address);
        this.currentAddressId = null;
        return "address/updateAddressPage";
    }
}
