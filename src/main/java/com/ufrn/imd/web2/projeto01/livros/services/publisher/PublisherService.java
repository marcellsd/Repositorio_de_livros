package com.ufrn.imd.web2.projeto01.livros.services.publisher;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;

@Service
public interface PublisherService {
    public Publisher savePublisher(Publisher publisher);
    public Publisher getPublisherById(Integer id);
    public InfoPublisherDTO getPublisherDTOById(Integer id);
    public void deletePublisherById(Integer id);
    public List<Publisher> getPublishersList();
    public List<InfoPublisherDTO> getPublishersDTOList();
    public Publisher updateById(Integer currentPublisherId, Publisher newPublisher);
}
