package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;


@Controller
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;
    Integer currentPublisherId = null;

    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;

    @RequestMapping("/getPublishersList")
    public String showListaCursos(Model model){
        List<Publisher> publishers = publisherService.getPublishersList();
        model.addAttribute("publishers",publishers);
        return "publisher/publisherList";
    }

    @RequestMapping("/showFormPublisher")
    public String showFormCurso(Model model){
        model.addAttribute("address", new Address());
        model.addAttribute("publisher", new Publisher());
        return "publisher/formPublisher";
    }

    @RequestMapping("/addPublisher")
    public String addPublisher(@ModelAttribute("publisher") Publisher publisher, 
                                @ModelAttribute("address") Address address, Model model){
        //Address newAddress = addressService.saveAddress(address);
        publisher.setAddress(addressService.saveAddress(address));
        Publisher newPublisher = publisherService.savePublisher(publisher);
        model.addAttribute("publisher", newPublisher);
        return "redirect:getPublishersList";
    }
    
    // @RequestMapping("/deletePublisher/{publisherId}")
    // public String deletePublisher(@PathVariable String publisherId, Model model){
    //     Integer id = Integer.parseInt(publisherId);
    //     Publisher publisher =  publisherService.getPublisherById(id);
    //     publisherService.deletePublisherById(id);
    //     model.addAttribute("publisher", publisher);
    //     return "publisher/deletePublisherPage";
    // }

    @GetMapping("deletePublisher")
    public String deletePublisher(@RequestParam(name = "id") Integer id) {
        publisherService.deletePublisherById(id);
        return "redirect:getPublishersList";
    }

    @RequestMapping("/getPublisher/{publisherId}")
    public String getPublisherById(@PathVariable String publisherId, Model model){
        Integer id = Integer.parseInt(publisherId);
        Publisher publisher =  publisherService.getPublisherById(id);
        model.addAttribute("publisher", publisher);
        return "publisher/publisherPage";
    }
    
    @RequestMapping("/showFormPublisherUpdate/{publisherId}")
    public String showFormPublisherUpdate(@PathVariable String publisherId, @ModelAttribute("publisher") Publisher publisher, Model model){
        Integer id = Integer.parseInt(publisherId);
        this.currentPublisherId = id;
        publisher =  publisherService.getPublisherById(id);
        model.addAttribute("publisher", publisher);
        System.out.println(publisher);
        return "publisher/formUpdatePublisher";
    }

    @RequestMapping("/updatePublisher")
    public String updatePublisher(@ModelAttribute("publisher") Publisher newPublisher, Model model){
        Publisher publisher = publisherService.updateById(currentPublisherId,newPublisher);
        System.out.println(publisher);
        model.addAttribute("publisherAtualizado", publisher);
        this.currentPublisherId = null;
        return "publisher/updatePublisherPage";
    }
}
