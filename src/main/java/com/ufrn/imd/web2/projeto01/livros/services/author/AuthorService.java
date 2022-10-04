package com.ufrn.imd.web2.projeto01.livros.services.author;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.models.Author;

@Service
public interface AuthorService {
    public Author saveAuthor(Author author);
    public void deleteAuthorById(Integer id);
    public Author getAuthorById(Integer id);
    public List<Author> getAuthorsList();
	public Author updateById(Integer currentAuthorId, Author newAuthor);
}
