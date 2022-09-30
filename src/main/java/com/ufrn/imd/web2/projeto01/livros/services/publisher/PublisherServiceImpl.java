package com.ufrn.imd.web2.projeto01.livros.services.publisher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    
    
}
