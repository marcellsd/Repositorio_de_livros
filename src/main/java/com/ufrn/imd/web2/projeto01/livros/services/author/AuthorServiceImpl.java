package com.ufrn.imd.web2.projeto01.livros.services.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.repositories.AuthorRepository;

@Component
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public void deleteAuthorById(Integer id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author getAuthorById(Integer id) {
        
        return authorRepository.findById(id).map(author -> {
            return author;
        }).orElseThrow();
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthorsList() {
        return authorRepository.findAll();
    }

	@Override
	public Author updateById(Integer currentAuthorId, Author newAuthor) {
        newAuthor.setId(currentAuthorId);
		return authorRepository.save(newAuthor);
	}
    
    
}
