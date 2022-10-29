package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;


@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;
    
    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;

    @GetMapping
    public List<Publisher> getPublisherList() {
        return publisherService.getPublishersList();
    }

    @GetMapping("{id}")
    public Publisher getPublisherById(@PathVariable Integer id) {
        return publisherService.getPublisherById(id);
    }

    @PostMapping()
    public Publisher savePublisher(@RequestBody Publisher publisher) {
        return publisherService.savePublisher(publisher);
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