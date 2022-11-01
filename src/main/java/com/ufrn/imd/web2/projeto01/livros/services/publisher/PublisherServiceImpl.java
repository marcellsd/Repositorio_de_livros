package com.ufrn.imd.web2.projeto01.livros.services.publisher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.repositories.PublishedRepository;

@Component
public class PublisherServiceImpl implements PublisherService{

    @Autowired
    PublishedRepository publishedRepository;

    @Override
    public void deletePublisherById(Integer id) {
        publishedRepository.deleteById(id);
    }

    @Override
    public Publisher getPublisherById(Integer id) {
        return publishedRepository.findById(id).map(publisher -> {
            return publisher;
        }).orElseThrow();
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return publishedRepository.save(publisher);
    }

	@Override
	public List<Publisher> getPublishersList() {
		return publishedRepository.findAll();
	}

	@Override
	public Publisher updateById(Integer currentPublisherId, Publisher newPublisher) {
       
            newPublisher.setId(currentPublisherId);
           
            return publishedRepository.save(newPublisher);
        
	}

    @Override
    public List<InfoPublisherDTO> getPublishersDTOList() {
		List<Publisher> publishers = publishedRepository.findAll();

        List<InfoPublisherDTO> publisherDTOs = new ArrayList<InfoPublisherDTO>();

        for (Publisher publisher : publishers) {
            List<InfoBookPublisherDTO> booksDTO = BooksToBooksDTO(publisher.getBooks());

            InfoPublisherDTO publisherDTO = InfoPublisherDTO.builder()
                                                        .id(publisher.getId())
                                                        .name(publisher.getName())
                                                        .books(booksDTO)
                                                        .address(InfoAddressPublisherDTO.builder()
                                                                 .id(publisher.getAddress().getId())
                                                                 .webSiteAddress(publisher.getAddress().getWebSiteAddress())
                                                                 .hqAddress(publisher.getAddress().getHqAddress())
                                                                 .build())
                                                        .build();
            publisherDTOs.add(publisherDTO);
        }

        return publisherDTOs;
	}

    @Override
    public InfoPublisherDTO getPublisherDTOById(Integer id) {
        Publisher publisher = publishedRepository.findById(id).map(publisherDB -> {
            return publisherDB;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editora não encontrada"));

        List<InfoBookPublisherDTO> booksDTO = BooksToBooksDTO(publisher.getBooks());

        InfoPublisherDTO publisherDTO = InfoPublisherDTO.builder()
                                                        .id(publisher.getId())
                                                        .name(publisher.getName())
                                                        .books(booksDTO)
                                                        .address(InfoAddressPublisherDTO.builder()
                                                                 .id(publisher.getAddress().getId())
                                                                 .webSiteAddress(publisher.getAddress().getWebSiteAddress())
                                                                 .hqAddress(publisher.getAddress().getHqAddress())
                                                                 .build())
                                                        .build();

        return publisherDTO;
    }

    private List<InfoBookPublisherDTO> BooksToBooksDTO(List<Book> books){
        if (books!=null){
            List<InfoBookPublisherDTO> booksDTO = new ArrayList<InfoBookPublisherDTO>();

            for (Book book : books) {
                InfoBookPublisherDTO bookDTO = InfoBookPublisherDTO.builder()
                                            .id(book.getId())
                                            .title(book.getTitle())
                                            .numberOfPages(book.getNumberOfPages())
                                            .edition(book.getEdition())
                                            .publicationDate(book.getPublicationDate())
                                            .isbn(book.getIsbn())
                                            .build();
                booksDTO.add(bookDTO);
            }

            return booksDTO;
        }

        return null;
    }
    
    
}
