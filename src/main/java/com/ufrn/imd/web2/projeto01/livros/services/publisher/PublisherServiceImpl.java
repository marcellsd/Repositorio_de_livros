package com.ufrn.imd.web2.projeto01.livros.services.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAddressPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.PublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.PublishedRepository;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;

@Component
public class PublisherServiceImpl implements PublisherService{

    @Autowired
    PublishedRepository publishedRepository;

    @Autowired
    RepoUserRepository repoUserRepository;

    @Autowired
    @Qualifier("addressServiceImpl")
    AddressService addressService;

    @Override
    public void deletePublisherById(Integer id) {
        Publisher oldPublisher = getPublisherById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(oldPublisher.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized deletion for this logged user");
           }
        publishedRepository.deleteById(id);
    }

    @Override
    public Publisher getPublisherById(Integer id) {
        return publishedRepository.findById(id).map(publisher -> {
            return publisher;
        }).orElseThrow();
    }

    @Override
    public Publisher savePublisher(PublisherDTO publisherDTO) {
        Address address = new Address();
        address.setHqAddress(publisherDTO.getHqAddress());
        address.setWebSiteAddress(publisherDTO.getWebSiteAddress());
        Address newAddress = addressService.saveAddress(address);
        Publisher publisher = new Publisher();
        publisher.setAddress(newAddress);
        publisher.setName(publisherDTO.getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<RepoUser> user = repoUserRepository.findByUsername(auth.getName());
        if(user != null) {
            publisher.setCreatorId(user.get().getId());
        }else {
            throw new NotFoundException("User not found");
        }
        return publishedRepository.save(publisher);
    }

	@Override
	public List<Publisher> getPublishersList() {
		return publishedRepository.findAll();
	}

	@Override
	public Publisher updatePutById(Integer currentPublisherId, PublisherDTO updatedPublisherDTO) {
        Publisher oldPublisher = getPublisherById(currentPublisherId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(oldPublisher.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
           }
        Publisher updatedPublisher = new Publisher();
        Address updatedAddress = oldPublisher.getAddress();

        updatedPublisher.setId(oldPublisher.getId());
        updatedPublisher.setCreatorId(oldPublisher.getCreatorId());
        updatedAddress.setId(oldPublisher.getAddress().getId());

        if(updatedPublisherDTO.getHqAddress() != null) updatedAddress.setHqAddress(updatedPublisherDTO.getHqAddress());
        if(updatedPublisherDTO.getWebSiteAddress() != null) updatedAddress.setWebSiteAddress(updatedPublisherDTO.getWebSiteAddress());
        if(updatedPublisherDTO.getName() == null) {
            updatedPublisher.setName(oldPublisher.getName());
        } else updatedPublisher.setName(updatedPublisherDTO.getName());

        updatedPublisher.setAddress(updatedAddress);

        return publishedRepository.save(updatedPublisher);
	}


	@Override
	public Publisher updatePatchById(Integer currentPublisherId, PublisherDTO updatedPublisherDTO) {
        Publisher oldPublisher = getPublisherById(currentPublisherId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(oldPublisher.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
           }
           Publisher updatedPublisher = new Publisher();
           Address updatedAddress = oldPublisher.getAddress();
   
           updatedPublisher.setId(oldPublisher.getId());
           updatedPublisher.setCreatorId(oldPublisher.getCreatorId());
           updatedAddress.setId(oldPublisher.getAddress().getId());
   
           if(updatedPublisherDTO.getHqAddress() != null) updatedAddress.setHqAddress(updatedPublisherDTO.getHqAddress());
           if(updatedPublisherDTO.getWebSiteAddress() != null) updatedAddress.setWebSiteAddress(updatedPublisherDTO.getWebSiteAddress());
           if(updatedPublisherDTO.getName() == null) {
               updatedPublisher.setName(oldPublisher.getName());
           } else updatedPublisher.setName(updatedPublisherDTO.getName());
           
           updatedPublisher.setAddress(updatedAddress);

           return publishedRepository.save(updatedPublisher);
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
        }).orElseThrow(() -> new NotFoundException("Publisher not found"));

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
