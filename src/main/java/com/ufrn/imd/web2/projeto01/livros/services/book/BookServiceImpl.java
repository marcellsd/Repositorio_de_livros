package com.ufrn.imd.web2.projeto01.livros.services.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.repositories.BookRepository;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
        
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).map(book -> {
            return book;
        }).orElseThrow();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

	@Override
	public List<Book> getBooksList() {
		return bookRepository.findAll();
	}

	@Override
	public Book updateById(Integer currentBookId, Book newBook) {
            newBook.setId(currentBookId);
            return bookRepository.save(newBook);
	}

    @Override
    public InfoBookDTO getBookDTOById(Integer id) {
        Book book = bookRepository.findById(id).map(bookBD -> {
            return bookBD;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
          
        List<InfoAuthorBookDTO> authorsDTO = authorToAuthorDTO(book.getAuthors());
        
        InfoBookDTO bookDTO = InfoBookDTO.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .numberOfPages(book.getNumberOfPages())
                                .edition(book.getEdition())
                                .publicationDate(book.getPublicationDate())
                                .isbn(book.getIsbn())
                                .authors(authorsDTO)
                                .publisher(InfoPublisherBookDTO.builder()
                                                                .id(book.getPublisher().getId())
                                                                .name(book.getPublisher().getName())
                                                                .build())
                                .build();

        return bookDTO;
    }
    
    @Override
    public List<InfoBookDTO> getBooksDTOList(){
        List<Book> books = bookRepository.findAll();
        List<InfoBookDTO> bookDTOs = new ArrayList<InfoBookDTO>();
        for (Book book : books) {
            List<InfoAuthorBookDTO> authorsDTO = authorToAuthorDTO(book.getAuthors());
            InfoBookDTO bookDTO = InfoBookDTO.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .numberOfPages(book.getNumberOfPages())
                                .edition(book.getEdition())
                                .publicationDate(book.getPublicationDate())
                                .isbn(book.getIsbn())
                                .authors(authorsDTO)
                                .publisher(InfoPublisherBookDTO.builder()
                                                                .id(book.getPublisher().getId())
                                                                .name(book.getPublisher().getName())
                                                                .build())
                                .build();
            bookDTOs.add(bookDTO);
            
        }

        return bookDTOs;
    }

    
    private List<InfoAuthorBookDTO> authorToAuthorDTO(List<Author> authors){
        if (authors!=null){
            List<InfoAuthorBookDTO> authorsDTO = new ArrayList<InfoAuthorBookDTO>();

            for (Author author : authors) {
                InfoAuthorBookDTO authorDTO = InfoAuthorBookDTO.builder()
                                            .id(author.getId())
                                            .name(author.getName())
                                            .build();
                authorsDTO.add(authorDTO);
            }

            return authorsDTO;
        }

        return null;
    }
}
