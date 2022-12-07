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
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;


@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;
    

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
        Publisher newPublisher = publisherService.savePublisher(publisherDTO);

        return publisherService.getPublisherDTOById(newPublisher.getId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisherById(@PathVariable Integer id) {
        publisherService.deletePublisherById(id);
    }

    @PutMapping("{id}")
    public void updatePublisher(@PathVariable Integer id, @RequestBody PublisherDTO updatedPublisher) {
        publisherService.updatePutById(id, updatedPublisher);
    }

    @PatchMapping("{id}")
    public void updatePublisherByPatch(@PathVariable Integer id, @RequestBody PublisherDTO updatedPublisher) {
        publisherService.updatePatchById(id, updatedPublisher);
    }

}