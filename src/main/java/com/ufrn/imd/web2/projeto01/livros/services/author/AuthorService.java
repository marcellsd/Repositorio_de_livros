package com.ufrn.imd.web2.projeto01.livros.services.author;

import com.ufrn.imd.web2.projeto01.livros.models.Author;

public interface AuthorService {
    public Author saveAuthor(Author author);
    public Author deleteAuthorById(Integer id);
    public Author findAuthorById(Integer id);
}
