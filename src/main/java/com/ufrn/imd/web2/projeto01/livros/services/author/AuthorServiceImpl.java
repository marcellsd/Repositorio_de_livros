package com.ufrn.imd.web2.projeto01.livros.services.author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
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
        Optional<Author> oldAuthor = authorRepository.findById(currentAuthorId);

        if(oldAuthor.isPresent()) {
           Author author =  oldAuthor.get();
            newAuthor.setId(currentAuthorId);
            newAuthor.setBooks(author.getBooks());
            return authorRepository.save(newAuthor);
        }else {
            return null;
        }
	}

    @Override
    public InfoAuthorDTO getAuthorDTOById(Integer id) {
        Author author = authorRepository.findById(id).map(authorBD -> {
            return authorBD;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
        
        List<InfoBookAuthorDTO> booksDTO = bookToBookDTO(author.getBooks());

        InfoAuthorDTO authorDTO = InfoAuthorDTO.builder()
                                  .id(author.getId())
                                  .name(author.getName())
                                  .books(booksDTO).build();

        return authorDTO;
    }
    
    @Override
    public List<InfoAuthorDTO> getAuthorsDTOList() {
        List<Author> authors = getAuthorsList();
        List<InfoAuthorDTO> authorDTOs = new ArrayList<InfoAuthorDTO>();

        for (Author author : authors) {
            List<InfoBookAuthorDTO> booksDTO = bookToBookDTO(author.getBooks());
            InfoAuthorDTO authorDTO = InfoAuthorDTO.builder()
                                      .id(author.getId())
                                      .name(author.getName())
                                      .books(booksDTO).build();
            
            authorDTOs.add(authorDTO);
        }


        return authorDTOs;
    }

    private List<InfoBookAuthorDTO> bookToBookDTO(List<Book> books){
        if (books!=null){
            List<InfoBookAuthorDTO> booksDTO = new ArrayList<InfoBookAuthorDTO>();

            for (Book book : books) {
                InfoBookAuthorDTO bookDTO = InfoBookAuthorDTO.builder()
                                            .id(book.getId())
                                            .title(book.getTitle())
                                            .numberOfPages(book.getNumberOfPages())
                                            .edition(book.getEdition())
                                            .publicationDate(book.getPublicationDate())
                                            .isbn(book.getIsbn())
                                            .publisher(InfoPublisherBookDTO.builder()
                                                                            .id(book.getPublisher().getId())
                                                                            .name(book.getPublisher().getName()).build())
                                            .build();
                booksDTO.add(bookDTO);
            }

            return booksDTO;
        }

        return null;
    }

 
}
