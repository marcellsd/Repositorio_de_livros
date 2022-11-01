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

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.PublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;


@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;
    
    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;

    @GetMapping
    public List<InfoPublisherDTO> getPublisherList() {
        return publisherService.getPublishersDTOList();
    }

    @GetMapping("{id}")
    public InfoPublisherDTO getPublisherById(@PathVariable Integer id) {
        return publisherService.getPublisherDTOById(id);
    }

    @PostMapping()
    public InfoPublisherDTO savePublisher(@RequestBody PublisherDTO publisherDTO) {
        Address address = new Address();
        address.setHqAddress(publisherDTO.getHqAddress());
        address.setWebSiteAddress(publisherDTO.getWebSiteAddress());
        Address newAddress = addressService.saveAddress(address);
        Publisher publisher = new Publisher();
        publisher.setAddress(newAddress);
        publisher.setName(publisherDTO.getName());
        Publisher newPublisher  = publisherService.savePublisher(publisher);
        newAddress.setPublisher(newPublisher);

        return publisherService.getPublisherDTOById(newPublisher.getId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisherById(@PathVariable Integer id) {
        publisherService.deletePublisherById(id);
    }

    @PutMapping("{id}")
    public void updatePublisher(@PathVariable Integer id, @RequestBody Publisher updatedPublisher) {
        Publisher oldPublisher = publisherService.getPublisherById(id);
        updatedPublisher.setId(oldPublisher.getId());
        publisherService.savePublisher(oldPublisher);
    }

    @PatchMapping("{id}")
    public void updatePublisherByPatch(@PathVariable Integer id, @RequestBody Publisher updatedPublisher) {
        Publisher oldPublisher = publisherService.getPublisherById(id);
        updatedPublisher.setId(oldPublisher.getId());
        if(updatedPublisher.getAddress() == null) updatedPublisher.setAddress(oldPublisher.getAddress());
        if(updatedPublisher.getBooks() == null) updatedPublisher.setBooks(oldPublisher.getBooks());
        if(updatedPublisher.getName() == null) updatedPublisher.setName(oldPublisher.getName());
        publisherService.savePublisher(oldPublisher);
    }

}