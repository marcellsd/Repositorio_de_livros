package com.ufrn.imd.web2.projeto01.livros.services.author;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;

@Service
public interface AuthorService {
    public Author saveAuthor(Author author);
    public void deleteAuthorById(Integer id);
    public Author getAuthorById(Integer id);
    public InfoAuthorDTO getAuthorDTOById(Integer id);
    public List<Author> getAuthorsList();
    public List<InfoAuthorDTO> getAuthorsDTOList();
	public Author updateById(Integer currentAuthorId, Author newAuthor);
}
